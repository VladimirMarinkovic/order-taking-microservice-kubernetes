package vlada.spring.customerdetailsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.customerdetailsservice.dto.CustomerDto;
import vlada.spring.customerdetailsservice.dto.RegisterRequest;
import vlada.spring.customerdetailsservice.exception.ApiResponse;
import vlada.spring.customerdetailsservice.service.PersonalDetailsService;
import javax.validation.Valid;



@RestController
@RequestMapping("/customers")
public class PersonalDetailsController {


    @Autowired
    PersonalDetailsService personalDetailsService;


    @GetMapping("/{contractNumber}")
    public CustomerDto findByContractNumber(@PathVariable String contractNumber) {
        return personalDetailsService.findByContractNumber(contractNumber);
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (personalDetailsService.contractNumberAreadyExist(registerRequest)) {
            return new ResponseEntity<>(new ApiResponse(400, "Customer with contract number: "
                    + registerRequest.getContractNumber() + " already exists"), HttpStatus.BAD_REQUEST);
        }
        personalDetailsService.registerCustomer(registerRequest);
        return new ResponseEntity<>(new ApiResponse(200, "Registration Successful!"), HttpStatus.OK);
    }
}
