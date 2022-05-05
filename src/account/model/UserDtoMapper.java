package account.model;

public class UserDtoMapper {
    public static UserDtoResponse mapToDtoResponse(UserDtoRequest user) {
        return new UserDtoResponse(
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
    }

}
