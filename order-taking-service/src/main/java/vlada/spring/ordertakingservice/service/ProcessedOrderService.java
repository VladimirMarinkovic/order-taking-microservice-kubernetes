package vlada.spring.ordertakingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import vlada.spring.ordertakingservice.configuration.RabbitMqConfig;
import vlada.spring.ordertakingservice.dto.OrderDto;
import vlada.spring.ordertakingservice.dto.OrderRequest;
import vlada.spring.ordertakingservice.dto.ProcessedOrderDto;
import vlada.spring.ordertakingservice.exception.ApiException;
import vlada.spring.ordertakingservice.model.*;
import vlada.spring.ordertakingservice.model.Package;
import vlada.spring.ordertakingservice.repository.ProcessedOrderRepository;
import vlada.spring.ordertakingservice.repository.ProcessedPackageAtributeRepository;
import vlada.spring.ordertakingservice.repository.ProcessedPackageRepository;
import vlada.spring.ordertakingservice.repository.ProcessedProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProcessedOrderService {

    @Autowired
    private ProcessedOrderRepository processedOrderRepository;
    @Autowired
    private ProcessedProductRepository processedProductRepository;
    @Autowired
    private ProcessedPackageRepository processedPackageRepository;
    @Autowired
    private ProcessedPackageAtributeRepository processedPackageAtributeRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PersonalDetailsService personalDetailsService;
    @Autowired
    private RabbitTemplate rabbitTemplate;




    public List<OrderDto> findAllProcessedOrdersWithCustomerDetails() {
        List<ProcessedOrder> processedOrders = processedOrderRepository.findAll();
        return processedOrders.stream()
                .map( o -> mapProcessedOrderToOrderDtoWithCustomer(o)).collect(Collectors.toList());
    }



    public List<ProcessedOrderDto> findAllProcessedOrders() {
        List<ProcessedOrder> processedOrders = processedOrderRepository.findAll();
       return processedOrders.stream().map(
                o -> mapProcessedOrderToProcessedOrderDto(o)).collect(Collectors.toList());
    }


    public List<ProcessedOrderDto> findProcessedOrderByCustomerContractNumber(String contractNumber) {
        List<ProcessedOrder> processedOrders = processedOrderRepository.findByCustomerContractNumber(contractNumber);
        return processedOrders.stream().map(
                o -> mapProcessedOrderToProcessedOrderDto(o)).collect(Collectors.toList());

    }


    public ProcessedOrderDto findProcessedOrderById(String id) {
        Optional<ProcessedOrder> optionalProcessedOrder = processedOrderRepository.findById(Long.parseLong(id));
        ProcessedOrder processedOrder = optionalProcessedOrder.orElseThrow(IllegalArgumentException::new);
        return mapProcessedOrderToProcessedOrderDto(processedOrder);
    }




    @Transactional
    public void editProcessedOrder(String id, OrderRequest orderRequest) {
        Optional<ProcessedOrder> optionalProcessedOrder = Optional.of(processedOrderRepository.getOne(Long.parseLong(id)));

        ProcessedOrder processedOrder = optionalProcessedOrder.orElseThrow(IllegalArgumentException::new);

        OrderDto orderDto = orderService.mapOrderRequestToOrderDto(orderRequest);
        ProcessedOrder editedOrder = orderService.mapOrderDtoToProcessedOrder(orderDto);

        if(processedOrder.getInstallationDate().isBefore(LocalDate.now().minusDays(1))) {
            processedOrder.setCustomerContractNumber(editedOrder.getCustomerContractNumber());
            processedOrder.setCustomerContractNumber(editedOrder.getInstallationAddress());
            processedOrder.setTimeSlotDetails(editedOrder.getTimeSlotDetails());
            processedOrder.setActive(editedOrder.isActive());
            processedOrder.setProducts(editedOrder.getProducts());
            processedOrder.setEdited(true);
            saveProcesedOrder(processedOrder);

            StopWatch stopWatch = new StopWatch(); stopWatch.start();
            orderDto.setEdited(true);
            rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_EXCHANGE,"vlada", orderDto);
            stopWatch.stop();
            log.info("Send edited order to Queue >> {}", orderDto.toString() + " Duration >> " + stopWatch.getTotalTimeSeconds());
        }
        else throw new ApiException("Order can only be  updated until 1 working day before the installation !");

    }


   public void cancleProcessedOrder(String id) {
       Optional<ProcessedOrder> optionalProcessedOrder = Optional.of(processedOrderRepository.getOne(Long.parseLong(id)));
       ProcessedOrder processedOrder = optionalProcessedOrder.orElseThrow(IllegalArgumentException::new);
       if(processedOrder.isActive() && processedOrder.getInstallationDate().isBefore(LocalDate.now().minusDays(1))) {
           processedOrder.setActive(false);
           OrderDto orderDto = mapProcessedOrderToOrderDtoWithCustomer(processedOrder);
           orderDto.setActive(false);
           StopWatch stopWatch = new StopWatch(); stopWatch.start();
           rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_EXCHANGE,"vlada", orderDto);
           stopWatch.stop();
           log.info("Send canceled order to Queue >> {}", orderDto.toString() + " Duration >> " + stopWatch.getTotalTimeSeconds());
           saveProcesedOrder(processedOrder);

       }
       else throw new ApiException("Only active order can be  canceled until 1 working day before the installation !");
   }



    @Transactional
    public void saveProcesedOrder(ProcessedOrder processedOrder) {

        List<Product> products = new ArrayList<>();
        for (Product p : processedOrder.getProducts()) {

            Product p1 = Product.builder().productName(p.getProductName()).build();
            processedProductRepository.save(p1);

            Package pa1 = Package.builder().packageName(p.getChosenPackage().getPackageName()).product(p1).build();
            p1.setChosenPackage(pa1);
            processedPackageRepository.save(pa1);

            for (PackageAtribute a : p.getChosenPackage().getPackageAtributes()) {
                a.setChosenPackage(p1.getChosenPackage());
                processedPackageAtributeRepository.save(a);

            }products.add(p1);
        }
        processedOrder.setProducts(products);
        processedOrderRepository.save(processedOrder);

    }





    public OrderDto mapProcessedOrderToOrderDtoWithCustomer(ProcessedOrder processedOrder) {
        Customer customer = personalDetailsService.getCustomerByContractNumber(processedOrder.getCustomerContractNumber());
        return OrderDto.builder()
                .customer(customer)
                .installationAddress(processedOrder.getInstallationAddress())
                .installationDate(processedOrder.getInstallationDate().toString())
                .timeSlotDetails(processedOrder.getTimeSlotDetails().toString())
                .products(processedOrder.getProducts().stream().
                        map(p -> orderService.mapProductToProductDto(p))
                        .collect(Collectors.toList()))
                .build();
    }


    public ProcessedOrderDto mapProcessedOrderToProcessedOrderDto(ProcessedOrder processedOrder) {
        return ProcessedOrderDto.builder()
                .customerContractNumber(processedOrder.getCustomerContractNumber())
                .installationAddress(processedOrder.getInstallationAddress())
                .installationDate(processedOrder.getInstallationDate().toString())
                .timeSlotDetails(processedOrder.getTimeSlotDetails().toString())
                .products(processedOrder.getProducts().stream().
                        map(p -> orderService.mapProductToProductDto(p))
                        .collect(Collectors.toList()))
                .build();
    }


}
