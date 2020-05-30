package vlada.spring.ordertakingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto implements Serializable {

    private String productId;
    private String productName;
    private PackageDto chosenPackage;

}
