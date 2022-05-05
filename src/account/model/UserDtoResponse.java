package account.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDtoResponse {

    private final String name;
    private final String lastname;
    private final String email;

}
