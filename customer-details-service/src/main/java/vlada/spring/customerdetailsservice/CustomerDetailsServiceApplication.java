package vlada.spring.customerdetailsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vlada.spring.customerdetailsservice.dto.RegisterRequest;
import vlada.spring.customerdetailsservice.service.PersonalDetailsService;



@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class CustomerDetailsServiceApplication implements CommandLineRunner {

    @Autowired
    PersonalDetailsService personalDetailsService;

    public static void main(String[] args) {
        SpringApplication.run(CustomerDetailsServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        RegisterRequest registerRequest1 = RegisterRequest.builder()
                .contractNumber("123456789")
                .email("marko@mail.com")
                .firstName("Marko")
                .lastName("Markovic")
                .address("Streat 7")
                .city("Belgrade")
                .telephone("+38163123456")
                .build();
        personalDetailsService.registerCustomer(registerRequest1);
    }
}
