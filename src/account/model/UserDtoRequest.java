package account.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
public class UserDtoRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@acme.com+$")
    private String email;
    @NotBlank
    private String password;


}
