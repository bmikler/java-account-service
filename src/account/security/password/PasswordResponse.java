package account.security.password;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PasswordResponse {
    private final String email;
    private final String status = "The password has been updated successfully";
}
