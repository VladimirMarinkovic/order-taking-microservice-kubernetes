package vlada.spring.ordertakingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is required")
    private String userName;

    @NotEmpty(message = "Password is required")
    private String password;

    @Transient
    @NotEmpty(message = "Password Confirmation is Required")
    private String passwordConfirmation;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;
}
