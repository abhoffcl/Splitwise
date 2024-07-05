package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAmount {
    private User user;
    private Double amount;

    public UserAmount(User user, Double amount) {
        this.user = user;
        this.amount = amount;
    }
}
