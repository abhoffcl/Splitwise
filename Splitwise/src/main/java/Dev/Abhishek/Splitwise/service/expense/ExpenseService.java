package Dev.Abhishek.Splitwise.service.expense;


import Dev.Abhishek.Splitwise.dto.ExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.ExpenseResponseDto;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequestDto ExpenseRequestDto);
    ExpenseResponseDto getExpense(int id);
    ExpenseResponseDto updateExpense(int id,ExpenseRequestDto ExpenseRequestDto);
    boolean deleteExpense(int id);
}
