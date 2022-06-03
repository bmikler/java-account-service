package account.admin;

import account.user.UserService;
import account.user.model.dto.UserDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @PutMapping("/admin/user/role")
    public ResponseEntity<?> setRoles(@RequestBody AdminRequest adminRequest){

        UserDtoResponse response = adminService.modifyRole(adminRequest);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/admin/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {

        userService.deleteUserByEmail(email);

        return ResponseEntity.ok()
                .body(Map.of("user", email, "status", "Deleted successfully!"));
    }

    @GetMapping("/admin/user")
    public ResponseEntity<?> getInfoAboutUsers() {

        return ResponseEntity.ok(userService.getAllUsersDesc());

    }

}
