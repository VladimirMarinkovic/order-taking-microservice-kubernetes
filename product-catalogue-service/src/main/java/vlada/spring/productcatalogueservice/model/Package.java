package vlada.spring.productcatalogueservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Package {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name of package is required")
    private String packageName;


    @OneToMany(mappedBy = "p")
    private List<PackageAtribute> packageAtributes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;



    public Package(String packageName,Product product) {
        this.packageName = packageName;
        this.product = product;
    }




}
