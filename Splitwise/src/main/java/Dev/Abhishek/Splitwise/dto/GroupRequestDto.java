package Dev.Abhishek.Splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupRequestDto {
    private String name;
    private List<Integer> memberId;
    private Integer createdBy;

}
