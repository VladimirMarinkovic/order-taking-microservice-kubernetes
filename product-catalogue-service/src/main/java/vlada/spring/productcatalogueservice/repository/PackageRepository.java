package vlada.spring.productcatalogueservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.productcatalogueservice.model.Package;
import vlada.spring.productcatalogueservice.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    Optional<Package> findByPackageName(String name);
}
