package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {
}
