package vlada.spring.ordertakingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import vlada.spring.ordertakingservice.exception.DateValidator;
import vlada.spring.ordertakingservice.exception.DateValidatorUsingDateFormat;
import vlada.spring.ordertakingservice.configuration.RabbitMqConfig;
import vlada.spring.ordertakingservice.dto.*;
import vlada.spring.ordertakingservice.exception.ApiException;
import vlada.spring.ordertakingservice.model.*;
import vlada.spring.ordertakingservice.model.Package;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderService {


    @Autowired
    private PersonalDetailsService personalDetailsService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailService mailService;
    @Autowired
    private TwilioService twilioService;
    @Autowired
    private ProcessedOrderService processedOrderService;


    public void processOrder(OrderDto orderDto) {
        orderDto.setActive(true);
        StopWatch stopWatch = new StopWatch(); stopWatch.start();
        rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_EXCHANGE,"vlada", orderDto);
        stopWatch.stop();
        log.info("Send order to Queue >> {}", orderDto.toString() + " Duration >> " + stopWatch.getTotalTimeSeconds());
        sendMail(orderDto);
//        sendSms(orderDto);
        stopWatch.start();
        processedOrderService.saveProcesedOrder(mapOrderDtoToProcessedOrder(orderDto));
        stopWatch.stop();
        log.info("Order successfully saved to database! >> Duration >> " + stopWatch.getTotalTimeSeconds());

    }




    public void sendMail(OrderDto orderDto) {
        List<String> p = orderDto.getProducts().stream().map(n -> n.getProductName()).collect(Collectors.toList());
        mailService.sendMail(orderDto.getCustomer().getEmail(),
                "Dear " + orderDto.getCustomer().getFirstName() + " " + orderDto.getCustomer().getLastName() + "," +
                        System.lineSeparator() + "Thank you for your trust in our company! Your order is in process." +
                        System.lineSeparator() + "Ordered products: " + p);
    }

    public void sendSms(OrderDto orderDto) {
                twilioService.sendSms(new Sms("+381691989889",
                "Thank you for your trust in our company! Your order is in process." + System.lineSeparator() +
                        "<< Order-Taking-Service >>"
                ));
    }




    public ProcessedOrder mapOrderDtoToProcessedOrder(OrderDto orderDto) {

        DateValidator validatorInstallDate = new DateValidatorUsingDateFormat("dd-MM-yyyy");
        DateValidator validatorInstallTimeSlot = new DateValidatorUsingDateFormat("dd-MM-yyyy HH:mm:ss");

        if(!validatorInstallDate.isValid(orderDto.getInstallationDate()))
            throw new ApiException("Instalation date must be in format:  dd-MM-yyyy");
        if(!validatorInstallTimeSlot.isValid(orderDto.getTimeSlotDetails()))
            throw new ApiException("Instalation Time Slot must be in format:  dd-MM-yyyy HH:mm:ss" );
        else {
            return ProcessedOrder.builder()
                    .customerContractNumber(orderDto.getCustomer().getContractNumber())
                    .installationAddress(orderDto.getInstallationAddress())
                    .installationDate(LocalDate.parse(orderDto.getInstallationDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .timeSlotDetails(LocalDateTime.parse(orderDto.getTimeSlotDetails(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                    .products(orderDto.getProducts().stream()
                            .map(p -> mapProductDtoToProduct(p)).collect(Collectors.toList())
                    )
                    .active(true)
                    .build();
        }
    }




    // Order With Existing Customer
    public OrderDto mapOrderRequestToOrderDto(OrderRequest orderRequest) {
        return OrderDto.builder()
                .customer(personalDetailsService.getCustomerByContractNumber(orderRequest.getCustomerContractNumber()))
                .installationAddress(orderRequest.getInstallationAddress())
                .installationDate(orderRequest.getInstallationDate())
                .timeSlotDetails(orderRequest.getTimeSlotDetails())
                .products(orderInfoService.getChosenProductsAndPackagesByName(orderRequest))
                .build();
    }




    // Order With New Customer
    public OrderDto mapOrderRequestNewCustomerToOrderDto(OrderRequestNewCustomer orderRequestNewCustomer) {
        Customer customer = personalDetailsService.getCustomerByContractNumber(
                orderRequestNewCustomer.getCustomerDto().getContractNumber());
        return OrderDto.builder()
                .customer(customer)
                .installationAddress(orderRequestNewCustomer.getInstallationAddress())
                .installationDate(orderRequestNewCustomer.getInstallationDate())
                .timeSlotDetails(orderRequestNewCustomer.getTimeSlotDetails())
                .products(orderInfoService.getChosenProductsAndPackagesByName(mapOrderRequestNewCustomerToOrderRequest(orderRequestNewCustomer)))
                .build();
    }
    public OrderRequest mapOrderRequestNewCustomerToOrderRequest(OrderRequestNewCustomer orderRequestNewCustomer) {
        return OrderRequest.builder()
                .products(orderRequestNewCustomer.getProducts())
                .customerContractNumber(orderRequestNewCustomer.getCustomerDto().getContractNumber())
                .installationAddress(orderRequestNewCustomer.getInstallationAddress())
                .installationDate(orderRequestNewCustomer.getInstallationDate())
                .timeSlotDetails(orderRequestNewCustomer.getTimeSlotDetails())
                .build();
    }




    public Product mapProductDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .productName(productDto.getProductName())
                .chosenPackage(mapPackageDtoToPackage(productDto.getChosenPackage()))
                .build();
    }


    public Package mapPackageDtoToPackage(PackageDto p) {
        return Package.builder()
                .packageName(p.getPackageName())
                .packageAtributes(
                        p.getPackageAtributes().stream().
                                map(a -> mapPackageAtributeToDto(a))
                                .collect(Collectors.toList()))
                .build();
    }


    public PackageAtribute mapPackageAtributeToDto(PackageAtributeDto packageAtributeDto) {
        return PackageAtribute.builder()
                .packageAtributeName(packageAtributeDto.getPackageAtributeName())
                .packageAtributeValue(packageAtributeDto.getPackageAtributeValue())
                .build();

    }

    public ProductDto mapProductToProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId().toString())
                .productName(product.getProductName())
                .chosenPackage(mapPackageToPackageDto(product.getChosenPackage()))
                .build();
    }

    public PackageDto mapPackageToPackageDto(Package p) {
        return PackageDto.builder()
                .packageId(p.getPackageId().toString())
                .packageName(p.getPackageName())
                .packageAtributes(
                        p.getPackageAtributes().stream().
                                map(a -> mapPackageAtributeToDto(a))
                                .collect(Collectors.toList()))
                .build();
    }


    public PackageAtributeDto mapPackageAtributeToDto(PackageAtribute packageAtribute) {
        return PackageAtributeDto.builder()
                .packageAtributeName(packageAtribute.getPackageAtributeName())
                .packageAtributeValue(packageAtribute.getPackageAtributeValue())
                .build();

    }

}
