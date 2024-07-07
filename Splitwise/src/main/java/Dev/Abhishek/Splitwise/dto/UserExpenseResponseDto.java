package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.User;
import Dev.Abhishek.Splitwise.entity.UserExpense;
import Dev.Abhishek.Splitwise.entity.UserExpenseType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpenseResponseDto {
    private Integer id;
    private Integer userId;
    private Double amount;
    private UserExpenseType type;


}
