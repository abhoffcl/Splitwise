package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExpenseRepository extends JpaRepository<UserExpense,Integer> {
}
