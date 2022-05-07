package account.user;

import account.security.password.PasswordRequest;
import account.security.password.PasswordResponse;
import account.user.model.User;
import account.user.model.dto.UserDtoMapper;
import account.user.model.dto.UserDtoRequest;
import account.user.model.dto.UserDtoResponse;
import account.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " user not found"));
    }

    public UserDtoResponse save(UserDtoRequest user) {

        if (user == null) {
            throw new IllegalArgumentException("User can`t be null!");
        }

        userRepository.findByEmailIgnoreCase(user.getEmail())
                .ifPresent(p -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
                });

        hashPassword(user);

        User userToSave = userDtoMapper.mapToUser(user);
        User userSaved = userRepository.save(userToSave);

        return userDtoMapper.mapToDtoResponse(userSaved);

    }

    private void hashPassword(UserDtoRequest userDtoRequest) {
        userDtoRequest.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));
    }

    public PasswordResponse updatePassword(User user, String newPassword) {

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return new PasswordResponse(user.getEmail());

    }
}
