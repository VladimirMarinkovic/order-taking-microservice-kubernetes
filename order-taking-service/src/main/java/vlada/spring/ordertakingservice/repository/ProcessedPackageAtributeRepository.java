package vlada.spring.ordertakingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.ordertakingservice.model.PackageAtribute;

@Repository
public interface ProcessedPackageAtributeRepository extends JpaRepository <PackageAtribute, Long> {
}
