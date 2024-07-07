package Dev.Abhishek.Splitwise.service.group;

import Dev.Abhishek.Splitwise.dto.ExpenseResponseDto;
import Dev.Abhishek.Splitwise.dto.GroupRequestDto;
import Dev.Abhishek.Splitwise.dto.GroupResponseDto;
import Dev.Abhishek.Splitwise.dto.SettlementTransactionResponseDto;
import Dev.Abhishek.Splitwise.entity.*;
import Dev.Abhishek.Splitwise.exception.GroupNotFoundException;
import Dev.Abhishek.Splitwise.exception.UserNotFoundException;
import Dev.Abhishek.Splitwise.repository.ExpenseRepository;
import Dev.Abhishek.Splitwise.repository.GroupRepository;
import Dev.Abhishek.Splitwise.repository.UserRepository;
import Dev.Abhishek.Splitwise.service.expense.ExpenseService;
import Dev.Abhishek.Splitwise.service.expense.ExpenseServiceImpl;
import Dev.Abhishek.Splitwise.service.strategy.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{
    private SettleUpStrategy settleUpStrategy;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private ExpenseService expenseService;
    private ExpenseRepository expenseRepository;

    @Autowired
    public GroupServiceImpl(SettleUpStrategy settleUpStrategy, GroupRepository groupRepository, UserRepository userRepository, ExpenseService expenseService, ExpenseRepository expenseRepository) {
        this.settleUpStrategy = settleUpStrategy;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.expenseService = expenseService;
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional
    public List<SettlementTransactionResponseDto> settleUp(int id) {
        Group savedGroup = groupRepository.findById(id).
                orElseThrow(()->new GroupNotFoundException("Group not found for id "+id));
        List<SettlementTransaction> settlementTransactions=savedGroup.getSettlementTransaction();
       // if(settlementTransactions==null || settlementTransactions.isEmpty()) {
            List<Expense>unSettledExpenses=expenseRepository.FindExpenseByGroupIdAndIsSettledFalse(id);
            settlementTransactions = settleUpStrategy.settleUp(unSettledExpenses);
            savedGroup.setSettlementTransaction(settlementTransactions);
            Group updatedGroup = groupRepository.save(savedGroup);

        List<SettlementTransactionResponseDto> settlementTransactionResponseDtos = settlementTransactions.
                    stream().
                    map(SettlementTransactionResponseDto::entityToSettlementTransactionResponseDto).
                    collect(Collectors.toList());
        return settlementTransactionResponseDtos;
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
        List<Expense>expenses=group.getExpenses();
        List<SettlementTransactionResponseDto> settlementTransactionDtos = settlementTransactions != null ?
                settlementTransactions.
                         stream()
                        .map(SettlementTransactionResponseDto::entityToSettlementTransactionResponseDto)
                        .collect(Collectors.toList()) : new ArrayList<>();
        List<ExpenseResponseDto>expenseDtos = expenses!=null?expenses.
                stream().
                map(expense->(((ExpenseServiceImpl)expenseService).entityToExpenseResponseDto(expense))).
                collect(Collectors.toList()) : new ArrayList<>();

        List<User>members =group.getMembers();
        List<Integer>memberIds=members!=null?members.
                stream().
                map(user->user.getId()).
                collect(Collectors.toList()) : new ArrayList<>();
        groupResponseDto.setMemberIds(memberIds);
        groupResponseDto.setId(group.getId());
        groupResponseDto.setTotalAmountSpent(group.getTotalAmountSpent());
        groupResponseDto.setSettlementTransactions(settlementTransactionDtos);
        groupResponseDto.setExpenses(expenseDtos);
        groupResponseDto.setName(group.getName());
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
        group.setTotalAmountSpent(0);
        return group;
    }
}
