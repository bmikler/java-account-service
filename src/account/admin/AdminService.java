package account.admin;

import account.user.UserRepository;
import account.user.UserService;
import account.user.model.User;
import account.user.model.UserRole;
import account.user.model.dto.UserDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static account.admin.AdminOperation.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserDtoResponse modifyRole(AdminRequest adminRequest) {

        User user = getUser(adminRequest.getUser());
        UserRole role = getRole(adminRequest.getRole());

        validateRequest(user, role, adminRequest.getOperation());

        UserDtoResponse response = userService.modifyRole(user, role, adminRequest.getOperation());

        return response;
    }

    private void validateRequest(User user, UserRole role, AdminOperation operation) {

        if (operation == GRANT){
            validateGrant(user, role);
        }

        if (operation == REMOVE) {
            validateRemove(user, role);
        }

    }

    private void validateRemove(User user, UserRole role) {
        if (role == UserRole.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }
        if (!user.getRoles().contains(role)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        }
        if (user.getRoles().size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        }
    }

    private void validateGrant(User user, UserRole role) {
        if (user.getRoles().contains(UserRole.ADMINISTRATOR) || role == UserRole.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
        }
    }

    private UserRole getRole(String role) {
        try {
            return UserRole.valueOf(role);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
    }

    private User getUser(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

}
