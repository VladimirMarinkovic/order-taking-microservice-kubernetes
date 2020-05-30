package vlada.spring.productcatalogueservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageAtributeDto {
    
    private String packageAtributeName;
    private String packageAtributeValue;
}
