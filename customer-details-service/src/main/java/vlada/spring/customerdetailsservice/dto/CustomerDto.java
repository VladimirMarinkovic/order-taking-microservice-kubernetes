package vlada.spring.customerdetailsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {

    private String id;
    private String contractNumber;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String email;
    private String telephone;
}
