package Dev.Abhishek.Splitwise.service.expense;


import Dev.Abhishek.Splitwise.dto.ExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.ExpenseResponseDto;
import Dev.Abhishek.Splitwise.dto.UserExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.UserExpenseResponseDto;
import Dev.Abhishek.Splitwise.entity.Expense;
import Dev.Abhishek.Splitwise.entity.Group;
import Dev.Abhishek.Splitwise.entity.User;
import Dev.Abhishek.Splitwise.entity.UserExpense;
import Dev.Abhishek.Splitwise.exception.ExpenseNotFoundException;
import Dev.Abhishek.Splitwise.exception.GroupNotFoundException;
import Dev.Abhishek.Splitwise.exception.UserNotFoundException;
import Dev.Abhishek.Splitwise.repository.ExpenseRepository;
import Dev.Abhishek.Splitwise.repository.GroupRepository;
import Dev.Abhishek.Splitwise.repository.UserRepository;
import Dev.Abhishek.Splitwise.service.UserExpense.UserExpenseService;
import Dev.Abhishek.Splitwise.service.UserExpense.UserExpenseServiceImpl;
import Dev.Abhishek.Splitwise.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseRepository expenseRepository;
    private UserExpenseService userExpenseService;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserExpenseService userExpenseService, GroupRepository groupRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userExpenseService = userExpenseService;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto) {
        int groupId=expenseRequestDto.getGroupId();
        Group group = groupRepository.findById(expenseRequestDto.getGroupId()).
                orElseThrow(()->new GroupNotFoundException("Group not found for id "+groupId ));
        Expense expense=expenseRequestDtoToEntity(expenseRequestDto);
        group.getExpenses().add(expense);
        groupRepository.save(group);
        return entityToExpenseResponseDto(expenseRepository.save(expense));
    }
    @Override
    public ExpenseResponseDto getExpense(int id) {
        return  entityToExpenseResponseDto(expenseRepository.findById(id).
                orElseThrow(()->new ExpenseNotFoundException("Expense not found for id "+id)));
    }

    @Override
    public ExpenseResponseDto updateExpense(int id, ExpenseRequestDto expenseRequestDto) {
        // not allowing to update members 
        Expense savedExpense = expenseRepository.findById(id).
                orElseThrow(()->new ExpenseNotFoundException("Expense not found for id "+id));
        savedExpense.setDescription(expenseRequestDto.getDescription());
        savedExpense.setAmount(expenseRequestDto.getAmount());
        savedExpense.setCurrency(expenseRequestDto.getCurrency());
        return entityToExpenseResponseDto(expenseRepository.save(savedExpense));
    }

    @Override
    public boolean deleteExpense(int id) {
        Expense expense= expenseRepository.findById(id).
                orElseThrow(()->new ExpenseNotFoundException("Expense not found for id "+id));
        expenseRepository.delete(expense);
        return true;
    }

    public Expense expenseRequestDtoToEntity(ExpenseRequestDto expenseRequestDto){
      Expense expense = new Expense();
      expense.setCurrency(expense.getCurrency());
      expense.setAmount(expenseRequestDto.getAmount());
      int addedById= expenseRequestDto.getAddedBy();
      User addedBy = userRepository.findById(addedById).
              orElseThrow(()->new UserNotFoundException("User adding expense not found for userId "+addedById));
      expense.setAddedBy(addedBy);
      expense.setExpenseTime(expense.getExpenseTime());
      return expense;
    }
    public ExpenseResponseDto entityToExpenseResponseDto(Expense expense){
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setCurrency(expense.getCurrency());
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setDescription(expense.getDescription());
        expenseResponseDto.setAddedBy(expense.getAddedBy().getId());
       /*
       find group by expenseId;
       Group group = groupRepository.findGroupByExpenseId(expense.getId());
      if(group!=null)
           expenseResponseDto.setGroupId(group.getId());
        else
            throw new GroupNotFoundException("Group not found for expense id "+expense.getId());
            not required */
        List<UserExpense>Userexpenses=expense.getUserExpenses();
        List<UserExpenseResponseDto> userExpensDtos=Userexpenses!=null?Userexpenses.
                stream().
                map(userExpense -> ((UserExpenseServiceImpl)userExpenseService).entityToUserExpenseResponseDto(userExpense)).
                collect(Collectors.toList()) : new ArrayList<>();
        expenseResponseDto.setUserExpenses(userExpensDtos);
        return expenseResponseDto;
    }

}
