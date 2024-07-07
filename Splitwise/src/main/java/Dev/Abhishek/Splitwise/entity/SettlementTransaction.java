package Dev.Abhishek.Splitwise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Currency;

@Getter
@Setter
@Entity
public class SettlementTransaction extends BaseModel{
    private double amount;
    @OneToOne
    @JoinColumn(name="paidTo")
    private User paidTo;
    @OneToOne
    @JoinColumn(name="paidBy")
    private User paidBy;
    private Instant time;

    public SettlementTransaction(double amount, User paidTo, User paidBy, Instant time) {
        this.amount = amount;
        this.paidTo = paidTo;
        this.paidBy = paidBy;
        this.time = time;
    }

    public SettlementTransaction() {
    }


}
