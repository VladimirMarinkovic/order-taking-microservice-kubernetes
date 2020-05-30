package vlada.spring.ordertakingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessingResponse {

    private Integer status;
    private String message;

}
