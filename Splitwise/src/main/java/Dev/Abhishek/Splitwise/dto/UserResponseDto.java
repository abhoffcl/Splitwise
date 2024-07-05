package Dev.Abhishek.Splitwise.dto;

import Dev.Abhishek.Splitwise.entity.Group;
import Dev.Abhishek.Splitwise.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private int id;
    private String name;
    private List<Integer> groupIds;
    private List<Integer>friends;


}
