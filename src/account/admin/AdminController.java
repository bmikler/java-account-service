package account.admin;

import account.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("admin/user/role")
    public ResponseEntity<?> setRoles(){
        throw new RuntimeException("Not implemented yet");
    }

    @DeleteMapping("api/admin/user")
    public ResponseEntity<?> deleteUser() {
        throw new RuntimeException("Not implemented yet");
    }

    @GetMapping("api/admin/user")
    public ResponseEntity<?> getInfoAboutUsers() {

        return ResponseEntity.ok(userService.getAllUsersDesc());

    }

}
