package vlada.spring.ordertakingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vlada.spring.ordertakingservice.model.Customer;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessedOrderDto {

    private String customerContractNumber;
    private String installationAddress;
    private String installationDate;
    private String timeSlotDetails;

    private List<ProductDto> products;
}
