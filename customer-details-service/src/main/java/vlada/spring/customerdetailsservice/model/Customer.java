package vlada.spring.customerdetailsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "Contract number is required")
    private String contractNumber;

    @NotEmpty(message = "Firs Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    private String city;

    private String address;


    @NotEmpty(message = "Telephone is required")
    private String telephone;
}
