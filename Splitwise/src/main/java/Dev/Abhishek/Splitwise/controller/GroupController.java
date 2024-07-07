package Dev.Abhishek.Splitwise.controller;

import Dev.Abhishek.Splitwise.dto.GroupRequestDto;
import Dev.Abhishek.Splitwise.dto.GroupResponseDto;
import Dev.Abhishek.Splitwise.dto.SettlementTransactionResponseDto;
import Dev.Abhishek.Splitwise.entity.SettlementTransaction;
import Dev.Abhishek.Splitwise.exception.InvalidInputException;
import Dev.Abhishek.Splitwise.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/settleUp/{groupId}")
    public ResponseEntity<List<SettlementTransactionResponseDto>> settleUp(@PathVariable("groupId")int groupId){
        if( groupId<1)
            throw new InvalidInputException("Enter valid groupId");
        return ResponseEntity.ok(groupService.settleUp(groupId));

    }
    @PostMapping("/")
    ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        String groupName= groupRequestDto.getName();
            if(groupName==null || groupName.isBlank() || groupName.isEmpty())
                throw new InvalidInputException("Enter proper group name");
        return ResponseEntity.ok(groupService.createGroup(groupRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> updateGroup(@PathVariable("id") int id, @RequestBody GroupRequestDto groupRequestDto) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Group id ");
        String groupName= groupRequestDto.getName();
        if(groupName!=null)
            if(groupName.isBlank() || groupName.isEmpty())
                throw new InvalidInputException("Enter proper group name");
        return ResponseEntity.ok(groupService.updateGroup(id, groupRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroup(@PathVariable("id") int id) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Group id ");
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("id") int id) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Group id ");
        return ResponseEntity.ok(groupService.deleteGroup(id));
    }
}