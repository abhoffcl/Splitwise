package Dev.Abhishek.Splitwise.dto;


import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Currency;

@Getter
@Setter
public class SettlementTransactionResponseDto {
    private Integer id;
    private double amount;
    private Integer paidTo;
    private Integer paidBy;
    private Instant time;

    public static SettlementTransactionResponseDto entityToSettlementTransactionResponseDto(SettlementTransaction settlementTransaction){
        SettlementTransactionResponseDto settlementTransactionResponseDto = new SettlementTransactionResponseDto();
        settlementTransactionResponseDto.setId(settlementTransaction.getId());
        settlementTransactionResponseDto.setAmount(settlementTransaction.getAmount());
        settlementTransactionResponseDto.setPaidBy(settlementTransaction.getPaidBy().getId());
        settlementTransactionResponseDto.setPaidTo(settlementTransaction.getPaidTo().getId());
        settlementTransactionResponseDto.setTime(settlementTransaction.getTime());

        return settlementTransactionResponseDto;
    }
}
