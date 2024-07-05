package Dev.Abhishek.Splitwise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity(name = "SPLITWISE_GROUP")
public class Group extends BaseModel{
    private String name;
    @ManyToOne
    private User createdBy;
    private Instant creationDate;
    private double totalAmountSpent;
    @ManyToMany
    private List<User>members;
    @OneToMany
    @JoinColumn(name = "groupId")
    private List<SettlementTransaction> settlementTransaction;
    @OneToMany
    @JoinColumn(name = "groupId")
    private List<Expense>expenses;

}
