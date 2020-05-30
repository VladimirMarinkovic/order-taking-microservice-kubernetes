package vlada.spring.ordertakingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vlada.spring.ordertakingservice.dto.UserLoginRequest;
import vlada.spring.ordertakingservice.dto.UserRegistrationRequest;
import vlada.spring.ordertakingservice.exception.ApiResponse;
import vlada.spring.ordertakingservice.security.AuthenticationResponse;
import vlada.spring.ordertakingservice.service.AuthService;


import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthService authService;

    @GetMapping("/test")
    public String test() {
        return "{test:true}" ;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequestDto) {
        return new ResponseEntity<>(authService.login(userLoginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        if (authService.userNameAreadyExist(userRegistrationRequest)) {
            return new ResponseEntity<>(new ApiResponse(400, "Username already exists"), HttpStatus.BAD_REQUEST);
        }
        authService.registerUser(userRegistrationRequest);
        return new ResponseEntity<>(new ApiResponse(200, "Registration Successful!"), HttpStatus.OK);
    }


}
