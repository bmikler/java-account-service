package account.admin;

import account.user.UserService;
import account.user.model.User;
import account.user.model.UserRole;
import account.user.model.dto.UserDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    public UserDtoResponse changeRole(AdminRoleRequest adminRequest) {

        User user = (User) userService.loadUserByUsername(adminRequest.getUser());
        UserRole role = UserRole.getRoleFromString(adminRequest.getRole());

        return userService.modifyRole(user, role, adminRequest.getOperation());
    }

    public UserDtoResponse changeAccess(AdminAccessRequest adminAccessRequest) {
        User user = (User) userService.loadUserByUsername(adminAccessRequest.getUser());
        return userService.modifyAccess(user, adminAccessRequest.getOperation());
    }

}
