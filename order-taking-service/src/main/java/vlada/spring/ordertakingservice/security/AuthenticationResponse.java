package vlada.spring.ordertakingservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String authenticationToken;
    private String username;

}
