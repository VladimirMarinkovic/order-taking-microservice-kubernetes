package vlada.spring.ordertakingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {


    @NotEmpty(message = "Customer contract number required.")
    @Size(min = 6, max = 9, message = "Contract number must be between 6 and 9 characters")
    private String customerContractNumber;

    @NotEmpty(message = "Instalation addres required.")
    private String installationAddress;

    @NotEmpty(message = "Instalation date required.")
    private String installationDate;

    @NotEmpty(message = "Instalation time slot required.")
    private String timeSlotDetails;

    private List< @Valid ProductDto> products;
}
