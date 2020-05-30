package vlada.spring.customerdetailsservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@Builder
public class RegisterRequest {

    @NotBlank
    private String contractNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String city;

    private String address;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String telephone;
}
