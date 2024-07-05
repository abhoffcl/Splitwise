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
    @Autowired
    private UserExpenseService userExpenseService;

    @PostMapping("/")
    ResponseEntity<UserExpenseResponseDto> createUserExpense(@RequestBody UserExpenseRequestDto userExpenseRequestDto) {

        return ResponseEntity.ok(userExpenseService.createUserExpense(userExpenseRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserExpenseResponseDto> updateUserExpense(@PathVariable("id") int id, @RequestBody UserExpenseRequestDto userExpenseRequestDto) {
        if (id < 1)
            throw new InvalidInputException("Enter valid UserExpense id ");
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