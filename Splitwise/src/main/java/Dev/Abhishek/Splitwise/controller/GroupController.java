package Dev.Abhishek.Splitwise.controller;

import Dev.Abhishek.Splitwise.dto.GroupRequestDto;
import Dev.Abhishek.Splitwise.dto.GroupResponseDto;
import Dev.Abhishek.Splitwise.exception.InvalidInputException;
import Dev.Abhishek.Splitwise.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/")
    ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupRequestDto groupRequestDto) {

        return ResponseEntity.ok(groupService.createGroup(groupRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> updateGroup(@PathVariable("id") int id, @RequestBody GroupRequestDto groupRequestDto) {
        if (id < 1)
            throw new InvalidInputException("Enter valid Group id ");
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