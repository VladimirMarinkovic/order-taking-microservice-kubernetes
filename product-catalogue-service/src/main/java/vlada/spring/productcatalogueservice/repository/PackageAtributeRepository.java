package vlada.spring.productcatalogueservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.productcatalogueservice.model.PackageAtribute;

@Repository
public interface PackageAtributeRepository extends JpaRepository<PackageAtribute, Long> {
}
