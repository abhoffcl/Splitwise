package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {
    @Query("SELECT e FROM SPLITWISE_GROUP g JOIN g.expenses e WHERE g.id=:groupId AND e.isSettled=FALSE")
    List<Expense>FindExpenseByGroupIdAndIsSettledFalse(@Param("groupId") int groupId);
}
