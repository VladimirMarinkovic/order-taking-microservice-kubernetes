package vlada.spring.ordertakingservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageRequest {

    @NotEmpty(message = "Package name required.")
    private String packageName;


}
