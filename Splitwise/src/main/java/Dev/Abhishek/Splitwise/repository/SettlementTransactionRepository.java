package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import Dev.Abhishek.Splitwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface SettlementTransactionRepository extends JpaRepository<SettlementTransaction,Integer> {
}
