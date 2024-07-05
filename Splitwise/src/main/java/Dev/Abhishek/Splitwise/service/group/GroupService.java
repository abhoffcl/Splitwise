package Dev.Abhishek.Splitwise.service.group;


import Dev.Abhishek.Splitwise.dto.GroupRequestDto;
import Dev.Abhishek.Splitwise.dto.GroupResponseDto;

public interface GroupService {
    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);
    GroupResponseDto getGroup(int id);
    GroupResponseDto updateGroup(int id,GroupRequestDto groupRequestDto);
    boolean deleteGroup(int id);
}
