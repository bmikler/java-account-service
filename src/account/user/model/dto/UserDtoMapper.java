package account.user.model.dto;

import account.user.model.UserRole;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDtoMapper {
    public UserDtoResponse mapToDtoResponse(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User can`t be null!");
        }

        return new UserDtoResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail().toLowerCase()

        );
    }

    public User mapToUser(UserDtoRequest userDtoRequest) {

        if (userDtoRequest == null) {
            throw new IllegalArgumentException("User can`t be null!");
        }

        return new User(
                userDtoRequest.getName(),
                userDtoRequest.getLastname(),
                userDtoRequest.getEmail(),
                userDtoRequest.getPassword(),
                UserRole.USER,
                new ArrayList<>(),
                true,
                false
        );

    }

    public User mapToAdministrator(UserDtoRequest userDtoRequest) {

        if (userDtoRequest == null) {
            throw new IllegalArgumentException("User can`t be null!");
        }

        return new User (
                userDtoRequest.getName(),
                userDtoRequest.getLastname(),
                userDtoRequest.getEmail(),
                userDtoRequest.getPassword(),
                UserRole.ADMINISTRATOR,
                new ArrayList<>(),
                true,
                false
        );
    }

}
