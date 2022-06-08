package account.user;

import account.admin.AdminOperation;
import account.user.model.User;
import account.user.model.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static account.admin.AdminOperation.GRANT;
import static account.admin.AdminOperation.REMOVE;

public class RoleChangeValidator {

    public static void validateRequest(User user, UserRole role, AdminOperation operation) {

        if (operation == GRANT){
            validateGrant(user, role);
        }

        if (operation == REMOVE) {
            validateRemove(user, role);
        }

    }

    private static void validateRemove(User user, UserRole role) {
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

    private static void validateGrant(User user, UserRole role) {
        if (user.getRoles().contains(UserRole.ADMINISTRATOR) || role == UserRole.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
        }
    }

}
