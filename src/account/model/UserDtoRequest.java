package account.model;


import account.security.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@acme.com+$")
    private String email;
    @NotBlank
    @Size(min = 13, message = "The password length must be at least 12 chars!")
    @Password(message = "The password is in the hacker's database!")
    private String password;


}
