package account.user;

import account.admin.AdminOperation;
import account.security.password.PasswordResponse;
import account.user.model.User;
import account.user.model.UserRole;
import account.user.model.dto.UserDtoMapper;
import account.user.model.dto.UserDtoRequest;
import account.user.model.dto.UserDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static account.admin.AdminOperation.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException( HttpStatus.NOT_FOUND, "User not found!"));
    }

    public List<UserDtoResponse> getAllUsersDesc() {
        return userRepository
                .findAll()
                .stream()
                .map(userDtoMapper::mapToDtoResponse)
                .sorted(Comparator.comparing(UserDtoResponse::getId))
                .collect(Collectors.toList());
    }

    public UserDtoResponse save(UserDtoRequest user) {
        if (user == null) {
            throw new IllegalArgumentException("User can`t be null!");
        }

        if (isUserExist(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }

        hashPassword(user);

        User userToSave = userRepository.findAll().size() > 0 ? userDtoMapper.mapToUser(user) : userDtoMapper.mapToAdministrator(user);

        User userSaved = userRepository.save(userToSave);

        return userDtoMapper.mapToDtoResponse(userSaved);

    }

    private boolean isUserExist(String email) {
        return userRepository.findByEmailIgnoreCase(email).isPresent();
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

    public void deleteUserByEmail(String email) {

        User user = (User) loadUserByUsername(email);

        if (user.getRoles().contains(UserRole.ADMINISTRATOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        }

        userRepository.delete(user);
    }

    @Transactional
    public UserDtoResponse modifyRole(User user, UserRole role, AdminOperation operation) {

        if (operation == GRANT) {
            user.addRole(role);
        }

        if (operation == REMOVE){
            user.removeRole(role);
        }

        User userSaved = userRepository.save(user);

        return userDtoMapper.mapToDtoResponse(userSaved);

    }




}
