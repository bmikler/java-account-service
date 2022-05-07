package account.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

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

        User user = new User();
        user.setName(userDtoRequest.getName());
        user.setLastname(userDtoRequest.getLastname());
        user.setEmail(userDtoRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));
        user.setEnabled(true);
        user.setLocked(false);
        user.setRole(AppUserRole.USER);

        return user;
    }

}
