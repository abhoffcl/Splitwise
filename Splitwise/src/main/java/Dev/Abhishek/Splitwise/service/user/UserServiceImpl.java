package Dev.Abhishek.Splitwise.service.user;

import Dev.Abhishek.Splitwise.dto.*;
import Dev.Abhishek.Splitwise.entity.Group;
import Dev.Abhishek.Splitwise.entity.User;
import Dev.Abhishek.Splitwise.exception.InvalidInputException;
import Dev.Abhishek.Splitwise.exception.InvalidUserCredentials;
import Dev.Abhishek.Splitwise.exception.UserNotFoundException;
import Dev.Abhishek.Splitwise.repository.UserRepository;
import Dev.Abhishek.Splitwise.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private GroupService groupService;

    @Autowired
    public UserServiceImpl( UserRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
    }
    @Override
    public UserResponseDto signup(UserSignupRequestDto signupRequestDto) {
        if(userRepository.findUserByEmail(signupRequestDto.getEmail())!=null)
            throw new InvalidInputException("User with same email exists");
        User user = userRequestDtoToEntity(signupRequestDto);
        return  entityToUserResponseDto(userRepository.save(user));
    }
    @Override
    public UserResponseDto login(UserLoginRequestDto loginRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String email = loginRequestDto.getEmail();
        User savedUser = userRepository.findUserByEmail(email);
        if(savedUser==null)
            throw new InvalidUserCredentials("User with email "+ email+" does not exist");
        if(encoder.matches(email, savedUser.getPassword() ))
            return entityToUserResponseDto(savedUser);
        else
            throw new InvalidUserCredentials("Password did not match");
    }
    @Override
    public UserResponseDto getUser(int id) {
        return  entityToUserResponseDto(userRepository.findById(id).
                orElseThrow(()->new UserNotFoundException("User not found for id "+id)));
    }
    @Override
    public UserResponseDto updateUser(int id, UserSignupRequestDto signupRequestDto) {
        if(userRepository.findUserByEmail(signupRequestDto.getEmail())!=null)
            throw new InvalidInputException("User with same email exists");
        User user = userRequestDtoToEntity(signupRequestDto);
        return  entityToUserResponseDto(userRepository.save(user));
    }
    @Override
    public boolean deleteUser(int id) {
        User user= userRepository.findById(id).
                orElseThrow(()->new UserNotFoundException("User not found for id "+id));
        userRepository.delete(user);
        return true;
    }
    public   UserResponseDto entityToUserResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(user.getName());
        userResponseDto.setId(user.getId());

        List<User>friends =user.getFriends();
        List<Integer> friendIds =friends!=null?friends.
                stream().
                map(friend->friend.getId()).
                collect(Collectors.toList()) : new ArrayList<>();
        List<Group>groups =user.getGroups();
        List<Integer> groupIds =groups!=null?groups.
                stream().
                map(group->group.getId()).
                collect(Collectors.toList()) : new ArrayList<>();

        userResponseDto.setFriends(friendIds);
        userResponseDto.setGroupIds(groupIds);
        return userResponseDto;
    }
    public User userRequestDtoToEntity(UserSignupRequestDto userRequestDto){
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(encoder.encode(userRequestDto.getPassword()));
        return user;
    }

}
