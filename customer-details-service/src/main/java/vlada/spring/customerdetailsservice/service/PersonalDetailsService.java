package vlada.spring.customerdetailsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vlada.spring.customerdetailsservice.dto.CustomerDto;
import vlada.spring.customerdetailsservice.dto.RegisterRequest;
import vlada.spring.customerdetailsservice.model.Customer;
import vlada.spring.customerdetailsservice.repository.CustomerRepository;

import java.util.Optional;

@Service
public class PersonalDetailsService {


    @Autowired
    CustomerRepository customerRepository;


    public boolean contractNumberAreadyExist(RegisterRequest registerRequest) {
        return customerRepository.existsByContractNumber(registerRequest.getContractNumber());
    }


    public void  registerCustomer(RegisterRequest registerRequest) {
        Customer customer = Customer.builder()
                .contractNumber(registerRequest.getContractNumber())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .telephone(registerRequest.getTelephone())
                .city(registerRequest.getCity())
                .address(registerRequest.getAddress())
                .build();
        customerRepository.save(customer);
    }



    public CustomerDto findByContractNumber(String contractNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findByContractNumber(contractNumber);
        Customer customer = optionalCustomer.orElseThrow(IllegalArgumentException::new);
        return mapCustomerToCustomerDto(customer);
    }



    public CustomerDto mapCustomerToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId().toString())
                .contractNumber(customer.getContractNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .telephone(customer.getTelephone())
                .city(customer.getCity())
                .address(customer.getAddress())
                .build();
    }





}
