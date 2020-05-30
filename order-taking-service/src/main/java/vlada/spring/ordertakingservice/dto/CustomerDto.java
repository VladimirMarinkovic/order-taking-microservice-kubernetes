package vlada.spring.ordertakingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    @NotEmpty(message = "Customer contract number required.")
    @Size(min = 6, max = 9, message = "Contract number must be between 6 and 9 characters")
    private String contractNumber;

    @NotEmpty(message = "First name required.")
    private String firstName;

    @NotEmpty(message = "Last name required.")
    private String lastName;

    private String city;
    private String address;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Telephone is required")
    private String telephone;
}
