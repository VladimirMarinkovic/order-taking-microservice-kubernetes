package vlada.spring.ordertakingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import vlada.spring.ordertakingservice.dto.CustomerDto;
import vlada.spring.ordertakingservice.feignclient.PersonalDetailsFeignClient;
import vlada.spring.ordertakingservice.model.Customer;



@Service
public class PersonalDetailsService {

    @Autowired
    private PersonalDetailsFeignClient personalDetailsFeignClient;



    public Customer getCustomerByContractNumber(@PathVariable("contractNumber") String contractNumber) {
        return personalDetailsFeignClient.getCustomerByContractNumber(contractNumber);
    }

    public void processCustomer(CustomerDto customerDto) {
        personalDetailsFeignClient.processCustomer(customerDto);
    }



}
