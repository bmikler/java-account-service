package account.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDtoResponse mapToDtoResponse(User user) {
        return new UserDtoResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail()
        );
    }

    public User mapToUser(UserDtoRequest userDtoRequest) {

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
