package account.admin;

import account.payment.model.Response;
import account.security.loggin.LoggingService;
import account.user.UserService;
import account.user.model.User;
import account.user.model.UserRole;
import account.user.model.dto.UserDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static account.admin.AdminOperation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;
    private final LoggingService loggingService;

    @PutMapping("/user/role")
    public ResponseEntity<?> manageRole(@RequestBody AdminRoleRequest adminRequest,
                                        @AuthenticationPrincipal User admin,
                                        HttpServletRequest request){

        UserDtoResponse response = adminService.changeRole(adminRequest);
        loggingService.roleChanged(admin.getEmail(),
                response.getEmail(),
                request.getRequestURI(),
                UserRole.getRoleFromString(adminRequest.getRole()),
                adminRequest.getOperation());

        return ResponseEntity.ok(response);

    }

    @PutMapping("/user/access")
    public ResponseEntity<?> manageAccess(@RequestBody AdminAccessRequest adminAccessRequest) {

        adminService.changeAccess(adminAccessRequest);

        return ResponseEntity.ok(
                new Response(
                        "User " + adminAccessRequest.getUser() + " " +
                                adminAccessRequest.getOperation()+"!"));

    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {

        userService.deleteUserByEmail(email);

        return ResponseEntity.ok()
                .body(Map.of("user", email, "status", "Deleted successfully!"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getInfoAboutUsers() {

        return ResponseEntity.ok(userService.getAllUsersDesc());

    }



}
