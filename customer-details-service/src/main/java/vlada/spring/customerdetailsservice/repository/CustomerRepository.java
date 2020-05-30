package vlada.spring.customerdetailsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.customerdetailsservice.model.Customer;
import java.util.Optional;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByContractNumber(String contractNumber);

    boolean existsByContractNumber(String contractNumber);
}
