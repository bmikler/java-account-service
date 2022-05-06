package account.service;

import account.model.User;
import account.model.UserDtoMapper;
import account.model.UserDtoRequest;
import account.model.UserDtoResponse;
import account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static account.model.UserDtoMapper.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserDtoResponse save(UserDtoRequest user) {

        userRepository.findByEmailIgnoreCase(user.getEmail())
                .ifPresent(p -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
                });

        User userToSave = userDtoMapper.mapToUser(user);

        User userSaved = userRepository.save(userToSave);

        return userDtoMapper.mapToDtoResponse(userSaved);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " user not found"));
    }
}
