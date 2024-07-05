package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementTransactionRepsository extends JpaRepository<SettlementTransaction,Integer> {
}
