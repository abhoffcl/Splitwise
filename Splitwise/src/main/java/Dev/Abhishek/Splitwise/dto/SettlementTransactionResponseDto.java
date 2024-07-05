package Dev.Abhishek.Splitwise.dto;


import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
public class SettlementTransactionResponseDto {
    private Currency currency;
    private double amount;
    private Integer paidTo;
    private Integer paidBy;

    public static SettlementTransactionResponseDto entityToSettlementTransactionResponseDto(SettlementTransaction settlementTransaction){
        SettlementTransactionResponseDto settlementTransactionResponseDto = new SettlementTransactionResponseDto();
        settlementTransactionResponseDto.setAmount(settlementTransaction.getAmount());
        settlementTransactionResponseDto.setCurrency(settlementTransaction.getCurrency());
        settlementTransactionResponseDto.setPaidBy(settlementTransaction.getPaidBy().getId());
        settlementTransactionResponseDto.setPaidTo(settlementTransaction.getPaidTo().getId());
        return settlementTransactionResponseDto;
    }
}
