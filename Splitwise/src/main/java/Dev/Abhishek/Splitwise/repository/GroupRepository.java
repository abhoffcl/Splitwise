package Dev.Abhishek.Splitwise.repository;

import Dev.Abhishek.Splitwise.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Query("SELECT g FROM SPLITWISE_GROUP  g JOIN g.expenses e WHERE e.id =:expenseId")
    Group findGroupByExpenseId(@Param("expenseId")int expenseId);
}
