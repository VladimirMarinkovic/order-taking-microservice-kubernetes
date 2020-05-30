package vlada.spring.ordertakingservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vlada.spring.ordertakingservice.model.Customer;
import vlada.spring.ordertakingservice.model.Product;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto implements Serializable {

    private Customer customer;
    private String installationAddress;
    private String installationDate;
    private String timeSlotDetails;
    private boolean active;
    private boolean edited;

    private List<ProductDto> products;
}
