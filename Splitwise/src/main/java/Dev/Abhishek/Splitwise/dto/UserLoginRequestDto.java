package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class UserLoginRequestDto {
    private String email;
    private String password;


}
