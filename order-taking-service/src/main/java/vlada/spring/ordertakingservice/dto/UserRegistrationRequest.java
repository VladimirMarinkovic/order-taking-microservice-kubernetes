package vlada.spring.ordertakingservice.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserRegistrationRequest {

    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 6, max = 16)
    private String password;
    @Transient
    @NotBlank
    private String confirmPassword;
    @NotBlank
    @Email
    private String email;


}
