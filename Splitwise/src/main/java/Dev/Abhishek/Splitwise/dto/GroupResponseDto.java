package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.Group;
import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import Dev.Abhishek.Splitwise.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupResponseDto {
    private List<Integer> memberIds;
    private double totalAmountSpent;
    private int id;
    private List<SettlementTransactionResponseDto>settlementTransactions;
    private List<ExpenseResponseDto>expenses;
}
