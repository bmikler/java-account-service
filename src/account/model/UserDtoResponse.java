package account.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDtoResponse {

    private final long id;
    private final String name;
    private final String lastname;
    private final String email;

}
