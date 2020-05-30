package vlada.spring.ordertakingservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vlada.spring.ordertakingservice.model.Package;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotEmpty(message = "Product name required.")
    private String productName;

    private PackageDto chosenPackage;
}
