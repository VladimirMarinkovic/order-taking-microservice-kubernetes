package vlada.spring.ordertakingservice.feignclient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import vlada.spring.ordertakingservice.dto.CustomerDto;
import vlada.spring.ordertakingservice.model.Customer;


@FeignClient(name = "customer-details-service", fallback = PersonalDetailsFallback.class)
public interface PersonalDetailsFeignClient {


    @GetMapping("/customers/{contractNumber}")
    Customer getCustomerByContractNumber(@PathVariable("contractNumber") String contractNumber);

    @PostMapping("/customers/register")
    void processCustomer(@RequestBody CustomerDto customerDto);


}


@Component
class PersonalDetailsFallback implements PersonalDetailsFeignClient {

    @Override
    public Customer getCustomerByContractNumber(String contractNumber) {
       return Customer.builder()
                .firstName("Customers information not available at the moment.")
                .build();
    }

    @Override
    public void processCustomer(CustomerDto customerDto) {

    }
}