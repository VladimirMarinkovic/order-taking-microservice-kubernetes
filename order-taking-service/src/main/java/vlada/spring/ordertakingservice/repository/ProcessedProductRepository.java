package vlada.spring.ordertakingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.ordertakingservice.model.Product;

@Repository
public interface ProcessedProductRepository extends JpaRepository<Product, Long> {
}
