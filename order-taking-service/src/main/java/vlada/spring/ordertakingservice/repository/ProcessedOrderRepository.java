package vlada.spring.ordertakingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlada.spring.ordertakingservice.model.ProcessedOrder;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {

    List <ProcessedOrder> findByCustomerContractNumber(String contractNumber);

}
