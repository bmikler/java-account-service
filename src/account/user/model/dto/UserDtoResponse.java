package account.user.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDtoResponse {

    private final long id;
    private final String name;
    private final String lastname;
    private final String email;

}
