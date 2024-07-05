package Dev.Abhishek.Splitwise.service.UserExpense;

import Dev.Abhishek.Splitwise.dto.UserExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.UserExpenseResponseDto;

public interface UserExpenseService {
    UserExpenseResponseDto createUserExpense(UserExpenseRequestDto userExpenseRequestDto);
    UserExpenseResponseDto getUserExpense(int id);
    UserExpenseResponseDto updateUserExpense(int id,UserExpenseRequestDto userExpenseRequestDto);
    boolean deleteUserExpense(int id);
}
