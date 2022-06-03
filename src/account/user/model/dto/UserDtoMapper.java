package account.user.model.dto;

import account.user.model.UserRole;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

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
                user.getEmail().toLowerCase(),
                user.getRoles().stream()
                        .map(r -> "ROLE_" + r.name())
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList())

        );
    }

    public User mapToUser(UserDtoRequest userDtoRequest) {

        if (userDtoRequest == null) {
            throw new IllegalArgumentException("User can`t be null!");
        }

        return new User(
                userDtoRequest.getName(),
                userDtoRequest.getLastname(),
                userDtoRequest.getEmail().toLowerCase(),
                userDtoRequest.getPassword(),
                Set.of(UserRole.USER),
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
                userDtoRequest.getEmail().toLowerCase(),
                userDtoRequest.getPassword(),
                Set.of(UserRole.ADMINISTRATOR),
                new ArrayList<>(),
                true,
                false
        );
    }

}
