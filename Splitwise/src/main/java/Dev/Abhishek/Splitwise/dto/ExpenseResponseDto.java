package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.Expense;
import Dev.Abhishek.Splitwise.entity.UserExpense;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
public class ExpenseResponseDto {
    private Integer id;
    private Integer groupId;
    private String description;
    private Double amount;
    private Integer addedBy;
    private Currency currency;
    private List<UserExpenseResponseDto>userExpenses;
    private boolean isSettled;


}
