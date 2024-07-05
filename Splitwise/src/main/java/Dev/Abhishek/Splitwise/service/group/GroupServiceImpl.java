package Dev.Abhishek.Splitwise.service.group;

import Dev.Abhishek.Splitwise.dto.GroupRequestDto;
import Dev.Abhishek.Splitwise.dto.GroupResponseDto;
import Dev.Abhishek.Splitwise.dto.SettlementTransactionResponseDto;
import Dev.Abhishek.Splitwise.entity.Group;
import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import Dev.Abhishek.Splitwise.entity.User;
import Dev.Abhishek.Splitwise.exception.GroupNotFoundException;
import Dev.Abhishek.Splitwise.exception.UserNotFoundException;
import Dev.Abhishek.Splitwise.repository.GroupRepository;
import Dev.Abhishek.Splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class GroupServiceImpl implements GroupService{
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
        Group group = groupRequestDtoToEntity(groupRequestDto);
        return entityToGroupResponseDto(groupRepository.save(group));
    }
    @Override
    public GroupResponseDto getGroup(int id) {
        return  entityToGroupResponseDto(groupRepository.findById(id).
                orElseThrow(()->new GroupNotFoundException("Group not found for id "+id)));
    }

    @Override
    public GroupResponseDto updateGroup(int id, GroupRequestDto groupRequestDto) {
        // not allowing to update the members,only name can be updated
        Group savedGroup = groupRepository.findById(id).
                orElseThrow(()->new GroupNotFoundException("Group not found for id "+id));
        savedGroup.setName(groupRequestDto.getName());
        return entityToGroupResponseDto(groupRepository.save(savedGroup));
    }
    @Override
    public boolean deleteGroup(int id) {
        Group group= groupRepository.findById(id).
                orElseThrow(()->new GroupNotFoundException("Group not found for id "+id));
        groupRepository.delete(group);
        return true;
    }
    public  GroupResponseDto entityToGroupResponseDto(Group group) {
        GroupResponseDto groupResponseDto = new GroupResponseDto();
        List<SettlementTransaction>settlementTransactions=group.getSettlementTransaction();
        List<SettlementTransactionResponseDto> settlementTransactionDtos = settlementTransactions != null ?
                settlementTransactions.
                         stream()
                        .map(SettlementTransactionResponseDto::entityToSettlementTransactionResponseDto)
                        .collect(Collectors.toList()) : new ArrayList<>();

        List<User>members =group.getMembers();
        List<Integer>memberIds=members!=null?members.
                stream().
                map(user->user.getId()).
                collect(Collectors.toList()) : new ArrayList<>();
        groupResponseDto.setMemberIds(memberIds);
        groupResponseDto.setId(group.getId());
        groupResponseDto.setTotalAmountSpent(group.getTotalAmountSpent());
        groupResponseDto.setSettlementTransactions(settlementTransactionDtos);
        return groupResponseDto;
    }
    public Group groupRequestDtoToEntity(GroupRequestDto groupRequestDto){
        Group group = new Group();
        List<Integer>memberIds=groupRequestDto.getMemberId();
        List<User>members = memberIds!=null?memberIds.
                stream().
                map(memberId->userRepository.findById(memberId).
                        orElseThrow(()-> new UserNotFoundException("Group member not found for userId "+memberId))).
                collect(Collectors.toList()) :new ArrayList<>();

        group.setMembers(members);
        group.setName(groupRequestDto.getName());
        int createdById = groupRequestDto.getCreatedBy();
        User createdBy = userRepository.findById(createdById).
                orElseThrow(()-> new UserNotFoundException("User creating Group not found for userId "+createdById));
        group.setCreatedBy(createdBy);
        return group;
    }


}
