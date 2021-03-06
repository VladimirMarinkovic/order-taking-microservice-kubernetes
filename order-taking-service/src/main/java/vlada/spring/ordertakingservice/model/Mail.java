package vlada.spring.ordertakingservice.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {

    private String from;
    private String to;
    private String content;
    private String subject;
}
