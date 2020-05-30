package vlada.spring.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageAtribute {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Package atribute name is required")
    private String packageAtributeName;
    @NotBlank(message = "Package atribute value is required")
    private String packageAtributeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package p;


    public PackageAtribute(String packageAtributeName, String packageAtributeValue, Package p) {
        this.packageAtributeName = packageAtributeName;
        this.packageAtributeValue = packageAtributeValue;
        this.p = p;
    }
}

