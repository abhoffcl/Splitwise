package Dev.Abhishek.Splitwise.service.group;


import Dev.Abhishek.Splitwise.dto.GroupRequestDto;
import Dev.Abhishek.Splitwise.dto.GroupResponseDto;
import Dev.Abhishek.Splitwise.dto.SettlementTransactionResponseDto;

import java.util.List;

public interface GroupService {
    List<SettlementTransactionResponseDto> settleUp(int id);
    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);
    GroupResponseDto getGroup(int id);
    GroupResponseDto updateGroup(int id,GroupRequestDto groupRequestDto);
    boolean deleteGroup(int id);
}
