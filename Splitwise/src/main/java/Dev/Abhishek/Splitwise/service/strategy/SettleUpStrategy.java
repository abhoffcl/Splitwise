package Dev.Abhishek.Splitwise.service.strategy;

import Dev.Abhishek.Splitwise.entity.Expense;
import Dev.Abhishek.Splitwise.entity.SettlementTransaction;

import java.util.List;

public interface SettleUpStrategy {
    public List<SettlementTransaction> settleUp(List<Expense>expenses);
}
