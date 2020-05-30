package vlada.spring.ordertakingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sms {

    @NotBlank
    private  String phoneNumber;
    @NotBlank
    private String message;

}
