package vlada.spring.ordertakingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.ordertakingservice.dto.CustomerDto;
import vlada.spring.ordertakingservice.dto.ProcessingResponse;
import vlada.spring.ordertakingservice.model.Customer;
import vlada.spring.ordertakingservice.service.PersonalDetailsService;

import javax.validation.Valid;

@RestController
@RequestMapping("/costumer")
public class CustomerController {

    @Autowired
    private PersonalDetailsService personalDetailsService;


    @GetMapping("/customers/{contractNumber}")
    public ResponseEntity<Customer> getCustomerByContractNumber(@PathVariable("contractNumber") String contractNumber) {
        return new ResponseEntity<>(personalDetailsService.getCustomerByContractNumber(contractNumber), HttpStatus.OK);
    }



    @PostMapping("/processcustomer")
    public ResponseEntity<ProcessingResponse> processCustomer(@Valid @RequestBody CustomerDto customerDto) {
        personalDetailsService.processCustomer(customerDto);
        return new ResponseEntity<>(new ProcessingResponse(200, "Customer proccess successfully!"), HttpStatus.OK);
    }

}
