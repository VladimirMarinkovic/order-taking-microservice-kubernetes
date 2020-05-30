package vlada.spring.ordertakingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageDto implements Serializable {

    private String packageId;
    private String packageName;
    private List<PackageAtributeDto> packageAtributes;
}
