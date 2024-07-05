package Dev.Abhishek.Splitwise.service.user;

import Dev.Abhishek.Splitwise.dto.UserLoginRequestDto;
import Dev.Abhishek.Splitwise.dto.UserResponseDto;
import Dev.Abhishek.Splitwise.dto.UserSignupRequestDto;

public interface UserService {
    UserResponseDto signup(UserSignupRequestDto signupRequestDto);
    UserResponseDto login(UserLoginRequestDto loginRequestDto);
    UserResponseDto getUser(int id);
    UserResponseDto updateUser(int id,UserSignupRequestDto signupRequestDto);
    boolean deleteUser(int id);
}
