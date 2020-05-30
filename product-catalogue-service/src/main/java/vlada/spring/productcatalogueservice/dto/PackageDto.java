package vlada.spring.productcatalogueservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageDto {


    private String productId;
    private String packageId;
    private String packageName;


    private List<PackageAtributeDto> packageAtributes;


}
