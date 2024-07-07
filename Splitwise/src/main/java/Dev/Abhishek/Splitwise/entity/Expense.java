package Dev.Abhishek.Splitwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private String description;
    private double amount;
    private Currency currency;
    private Instant expenseTime;
    @ManyToOne
    private User addedBy;
    @OneToMany
    @JoinColumn(name = "expenseId")
    private List<UserExpense>userExpenses;
    private boolean isSettled;


}
