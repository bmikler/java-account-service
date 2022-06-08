package account.user.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum UserRole {
    USER, ACCOUNTANT, ADMINISTRATOR, AUDITOR;

    public static UserRole getRoleFromString(String role) {
        try {
            return UserRole.valueOf(role);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!");
        }
    }

}
