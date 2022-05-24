package account.user.model.dto;


import account.security.password.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@acme.com+$", message = "Email must be registered in acme.com domain")
    private String email;
    @NotBlank(message = "must not be blank")
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    @Password(message = "The password is in the hacker's database!")
    private String password;


}
