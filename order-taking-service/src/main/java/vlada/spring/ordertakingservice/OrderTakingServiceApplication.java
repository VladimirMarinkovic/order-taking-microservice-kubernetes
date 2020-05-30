package vlada.spring.ordertakingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vlada.spring.ordertakingservice.dto.UserRegistrationRequest;
import vlada.spring.ordertakingservice.repository.UserRepository;
import vlada.spring.ordertakingservice.service.AuthService;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
public class OrderTakingServiceApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OrderTakingServiceApplication.class);
    }

    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderTakingServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        if(!userRepository.existsByUserName("admin")) {
            UserRegistrationRequest userRegistrationRequest1 = UserRegistrationRequest.builder()
                    .userName("admin")
                    .password("1234")
                    .confirmPassword("1234")
                    .email("admin@mail.com")
                    .build();
            authService.registerUser(userRegistrationRequest1);
        }
    }
}