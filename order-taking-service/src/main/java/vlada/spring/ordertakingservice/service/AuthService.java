package vlada.spring.ordertakingservice.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vlada.spring.ordertakingservice.dto.UserLoginRequest;
import vlada.spring.ordertakingservice.dto.UserRegistrationRequest;
import vlada.spring.ordertakingservice.model.User;
import vlada.spring.ordertakingservice.repository.UserRepository;
import vlada.spring.ordertakingservice.security.AuthenticationResponse;
import vlada.spring.ordertakingservice.security.JwtProvider;



@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;


    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }



    public AuthenticationResponse login(UserLoginRequest userLoginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequest.getUserName(),
                userLoginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, userLoginRequest.getUserName());
    }


    public boolean userNameAreadyExist(UserRegistrationRequest userRegistrationRequest) {
        return userRepository.existsByUserName(userRegistrationRequest.getUserName());
    }


    public void  registerUser(UserRegistrationRequest userRegistrationRequest) {
        User user = User.builder()
                .userName(userRegistrationRequest.getUserName())
                .password(encodePassword(userRegistrationRequest.getPassword()))
                .passwordConfirmation(encodePassword(userRegistrationRequest.getConfirmPassword()))
                .email(userRegistrationRequest.getEmail())
                .build();
        userRepository.save(user);
    }









}
