package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.UserExpense;
import Dev.Abhishek.Splitwise.entity.UserExpenseType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpenseRequestDto {
    private Integer expenseId;
    private Integer userId;
    private double amount;
    private UserExpenseType type;


}



