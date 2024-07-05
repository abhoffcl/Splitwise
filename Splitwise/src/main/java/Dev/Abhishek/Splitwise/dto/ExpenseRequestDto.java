package Dev.Abhishek.Splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
public class ExpenseRequestDto {
    private Integer groupId;
    private String description;
    private Double amount;
    private Integer addedBy;
    private Currency currency;
}
