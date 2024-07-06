package Dev.Abhishek.Splitwise.controller;


import Dev.Abhishek.Splitwise.dto.UserLoginRequestDto;
import Dev.Abhishek.Splitwise.dto.UserResponseDto;
import Dev.Abhishek.Splitwise.dto.UserSignupRequestDto;
import Dev.Abhishek.Splitwise.exception.InvalidInputException;
import Dev.Abhishek.Splitwise.exception.InvalidUserCredentials;
import Dev.Abhishek.Splitwise.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    ResponseEntity<UserResponseDto> signup(@RequestBody UserSignupRequestDto userSignupRequestDto){
        String name=userSignupRequestDto.getName();
        if (name == null || name.isEmpty() || name.isEmpty()) {
            throw new InvalidInputException("name cannot be blank");
        }
        validateEmail(userSignupRequestDto.getEmail(),false);
        validatePassword(userSignupRequestDto.getPassword(),false);
        return ResponseEntity.ok(userService.signup(userSignupRequestDto));
    }
    @PostMapping("/login")
    ResponseEntity<UserResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        validateEmail(userLoginRequestDto.getEmail(),false);
        validatePassword(userLoginRequestDto.getPassword(),false);
        return ResponseEntity.ok(userService.login(userLoginRequestDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id")int id, @RequestBody UserSignupRequestDto userRequestDto){
        if(id<1)
            throw new InvalidInputException("Enter valid User id ");
        String name=userRequestDto.getName();
        String email=userRequestDto.getEmail();
        String password =userRequestDto.getPassword();
        if(name!=null)
            if(name.isEmpty() || name.isBlank())
                throw new InvalidInputException("Enter proper name to update");
        if(email!=null)
            validateEmail(email,true);
        if(password!=null)
            validatePassword(password,true);
        return ResponseEntity.ok(userService.updateUser(id,userRequestDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto>getUser(@PathVariable("id")int id){
        if(id<1)
            throw new InvalidInputException("Enter valid User id ");
        return ResponseEntity.ok(userService.getUser(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean>deleteUser(@PathVariable("id")int id){
        if(id<1)
            throw new InvalidInputException("Enter valid User id ");
        return ResponseEntity.ok(userService.deleteUser(id));
    }
    private void validateEmail(String email,boolean isUpdate) {
        if(!isUpdate) {
            if (email == null || email.isEmpty() || email.isEmpty()) {
                throw new InvalidUserCredentials("Email cannot be blank");
            }
        }
        // Validate email format using regex pattern
        final String EMAIL_PATTERN = "^(.+)@(.+)$";
        final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

        if (!emailPattern.matcher(email).matches()) {
            throw new InvalidUserCredentials("Invalid email format");
        }
    }
    private void validatePassword(String password,boolean isUpdate) {
        if(!isUpdate) {
            if (password == null || password.isEmpty() || password.isEmpty()) {
                throw new InvalidUserCredentials("password cannot be blank");
            }
        }
      final String PASSWORD_PATTERN =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
       final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        if (password == null || !passwordPattern.matcher(password).matches()) {
            throw new InvalidUserCredentials("Invalid password format-password should have " +
                    " atleast 8 characters including a small, capital, numeric and special character");
        }
    }
}