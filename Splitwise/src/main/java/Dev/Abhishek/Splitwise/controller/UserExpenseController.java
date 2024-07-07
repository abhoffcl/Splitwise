package Dev.Abhishek.Splitwise.controller;


import Dev.Abhishek.Splitwise.dto.UserExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.UserExpenseResponseDto;
import Dev.Abhishek.Splitwise.exception.InvalidInputException;
import Dev.Abhishek.Splitwise.service.UserExpense.UserExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userExpense")
public class UserExpenseController {

    private UserExpenseService userExpenseService;

    @Autowired
    public UserExpenseController(UserExpenseService userExpenseService) {
        this.userExpenseService = userExpenseService;
    }

    @PostMapping("/")
    ResponseEntity<UserExpenseResponseDto> createUserExpense(@RequestBody UserExpenseRequestDto userExpenseRequestDto) {
        Double amount = userExpenseRequestDto.getAmount();
        if(amount==null || amount<0)
            throw new InvalidInputException("Enter valid amount ");
        Integer expenseId=userExpenseRequestDto.getExpenseId();
        if (expenseId == null || expenseId<1)
            throw new IllegalArgumentException("Enter valid expense id");
        return ResponseEntity.ok(userExpenseService.createUserExpense(userExpenseRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserExpenseResponseDto> updateUserExpense(@PathVariable("id") int id, @RequestBody UserExpenseRequestDto userExpenseRequestDto) {
        if (id < 1)
            throw new InvalidInputException("Enter valid UserExpense id ");
        Double amount = userExpenseRequestDto.getAmount();
        if(amount!=null ){
            if(amount<0)
                throw new InvalidInputException("Enter valid amount ");
        }
        return ResponseEntity.ok(userExpenseService.updateUserExpense(id, userExpenseRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserExpenseResponseDto> getUserExpense(@PathVariable("id") int id) {
        if (id < 1)
            throw new InvalidInputException("Enter valid UserExpense id ");
        return ResponseEntity.ok(userExpenseService.getUserExpense(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserExpense(@PathVariable("id") int id) {
        if (id < 1)
            throw new InvalidInputException("Enter valid UserExpense id ");
        return ResponseEntity.ok(userExpenseService.deleteUserExpense(id));
    }
}