package vlada.spring.ordertakingservice.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Customer implements Serializable {

    private String id;
    private String contractNumber;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String email;
    private String telephone;
}
