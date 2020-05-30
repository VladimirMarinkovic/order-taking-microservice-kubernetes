package vlada.spring.ordertakingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.ordertakingservice.model.Package;

@Repository
public interface ProcessedPackageRepository extends JpaRepository<Package, Long> {
}
