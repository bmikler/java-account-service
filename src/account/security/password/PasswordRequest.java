package account.security.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    @JsonProperty("new_password")
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @Password(message = "The password is in the hacker's database!")
    @NotBlank
    private String password;
}
