package account.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminAccessRequest {

    @NotBlank
    private String user;
    @NotNull
    private AdminOperation operation;

}
