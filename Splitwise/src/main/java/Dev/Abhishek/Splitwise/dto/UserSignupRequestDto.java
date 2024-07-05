package Dev.Abhishek.Splitwise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDto {
    private String name;
    private String email;
    private String password;
}
