package Dev.Abhishek.Splitwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserExpense extends BaseModel{
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    private double amount;
    @Enumerated(EnumType.STRING)
    private UserExpenseType type;

}
