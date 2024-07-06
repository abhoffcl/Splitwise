package Dev.Abhishek.Splitwise.service.UserExpense;

import Dev.Abhishek.Splitwise.dto.UserExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.UserExpenseResponseDto;
import Dev.Abhishek.Splitwise.entity.Expense;
import Dev.Abhishek.Splitwise.entity.User;
import Dev.Abhishek.Splitwise.entity.UserExpense;
import Dev.Abhishek.Splitwise.exception.ExpenseNotFoundException;
import Dev.Abhishek.Splitwise.exception.UserExpenseNotFoundException;
import Dev.Abhishek.Splitwise.exception.UserNotFoundException;
import Dev.Abhishek.Splitwise.repository.ExpenseRepository;
import Dev.Abhishek.Splitwise.repository.UserExpenseRepository;
import Dev.Abhishek.Splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserExpenseServiceImpl implements UserExpenseService{
    private UserExpenseRepository userExpenseRepository;
    private UserRepository userRepository;
    private ExpenseRepository expenseRepository;
    
    @Autowired
    public UserExpenseServiceImpl(UserExpenseRepository userExpenseRepository, UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userExpenseRepository = userExpenseRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public UserExpenseResponseDto createUserExpense(UserExpenseRequestDto userExpenseRequestDto) {
        int expenseId=userExpenseRequestDto.getExpenseId();
        Expense expense = expenseRepository.findById(userExpenseRequestDto.getExpenseId()).
                orElseThrow(()->new ExpenseNotFoundException("Expense not found for id "+expenseId ));
        UserExpense userExpense=userExpenseRequestDtoToEntity(userExpenseRequestDto);
        List<UserExpense> userExpenses=expense.getUserExpenses()!=null?expense.getUserExpenses():new ArrayList<>();
        userExpenses.add(userExpense);
        expense.setUserExpenses(userExpenses);
        expenseRepository.save(expense);
        return entityToUserExpenseResponseDto(userExpenseRepository.save(userExpense));
    }
    @Override
    public UserExpenseResponseDto getUserExpense(int id) {
        return  entityToUserExpenseResponseDto(userExpenseRepository.findById(id).
                orElseThrow(()->new UserExpenseNotFoundException("UserExpense not found for id "+id)));
    }
    @Override
    public UserExpenseResponseDto updateUserExpense(int id, UserExpenseRequestDto userExpenseRequestDto) {
        UserExpense saveduserExpense = userExpenseRepository.findById(id).
                orElseThrow(()->new UserExpenseNotFoundException("userExpense not found for id "+id));
        saveduserExpense.setAmount(userExpenseRequestDto.getAmount());
       saveduserExpense.setType(userExpenseRequestDto.getType());
        return entityToUserExpenseResponseDto(userExpenseRepository.save(saveduserExpense));
    }
    @Override
    public boolean deleteUserExpense(int id) {
        UserExpense userExpense= userExpenseRepository.findById(id).
                orElseThrow(()->new UserExpenseNotFoundException("UserExpense not found for id "+id));
        userExpenseRepository.delete(userExpense);
        return true;
    }
    public  UserExpense userExpenseRequestDtoToEntity(UserExpenseRequestDto userExpenseRequestDto){
        UserExpense userExpense = new UserExpense();
        int userId = userExpenseRequestDto.getUserId();
        User user = userRepository.findById(userId).
                orElseThrow(()->new UserNotFoundException("User not found for id "+userId));
        userExpense.setUser(user);// need to User repository
        userExpense.setAmount(userExpenseRequestDto.getAmount());
        userExpense.setType(userExpenseRequestDto.getType());
        return userExpense;
    }
    public UserExpenseResponseDto entityToUserExpenseResponseDto(UserExpense userExpense){
        UserExpenseResponseDto userExpenseResponseDto = new UserExpenseResponseDto();
        userExpenseResponseDto.setUserId(userExpense.getUser().getId());
        userExpenseResponseDto.setAmount(userExpense.getAmount());
        userExpenseResponseDto.setType(userExpense.getType());
        return userExpenseResponseDto;
    }

}
