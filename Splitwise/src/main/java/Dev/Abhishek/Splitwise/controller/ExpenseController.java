package Dev.Abhishek.Splitwise.controller;

import Dev.Abhishek.Splitwise.dto.ExpenseRequestDto;
import Dev.Abhishek.Splitwise.dto.ExpenseResponseDto;
import Dev.Abhishek.Splitwise.exception.InvalidInputException;
import Dev.Abhishek.Splitwise.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/")
    ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody ExpenseRequestDto expenseRequestDto) {

        return ResponseEntity.ok(expenseService.createExpense(expenseRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable("id") int id, @RequestBody ExpenseRequestDto expenseRequestDto) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Expense id ");
        return ResponseEntity.ok(expenseService.updateExpense(id, expenseRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpense(@PathVariable("id") int id) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Expense id ");
        return ResponseEntity.ok(expenseService.getExpense(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable("id") int id) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Expense id ");
        return ResponseEntity.ok(expenseService.deleteExpense(id));
    }
}