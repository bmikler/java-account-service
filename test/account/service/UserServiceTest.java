package account.service;

import account.user.model.UserRole;
import account.user.model.User;
import account.user.model.dto.UserDtoMapper;
import account.user.model.dto.UserDtoRequest;
import account.user.model.dto.UserDtoResponse;
import account.user.UserRepository;
import account.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService service;
    private UserDtoMapper mapper;
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Before
    public void init() {
        mapper = mock(UserDtoMapper.class);
        repository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        service = new UserService(repository, mapper, passwordEncoder);
    }

    //save user exist/not exist/ null user
    @Test
    public void saveNullArgument(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.save(null));
        assertEquals(e.getMessage(), "User can`t be null!");
    }

    @Test
    public void saveUserOK() {
        UserDtoRequest userDtoRequest = new UserDtoRequest(
                "testName", "tesLastname", "mail@acme.com", "HashedTestPassword");

        when(repository.findByEmailIgnoreCase("mail@acme.com")).thenReturn(Optional.empty());

        User userToSave = new User();
        userToSave.setName("testName");
        userToSave.setLastname("testLastname");
        userToSave.setEmail("mail@acme.com");
        userToSave.setPassword("HashedTestPassword");
        userToSave.setEnabled(true);
        userToSave.setLocked(false);
        userToSave.setRole(UserRole.USER);

        when(mapper.mapToUser(userDtoRequest)).thenReturn(userToSave);

        User userSaved = new User();
        userToSave.setId(1L);
        userSaved.setName("testName");
        userSaved.setLastname("testLastname");
        userSaved.setEmail("mail@acme.com");
        userSaved.setPassword("HashedTestPassword");
        userSaved.setEnabled(true);
        userSaved.setLocked(false);
        userSaved.setRole(UserRole.USER);

        when(repository.save(userToSave)).thenReturn(userSaved);

        UserDtoResponse userExpected = new UserDtoResponse(1L, "testName", "testLastname", "mail@acme.com");

        when(mapper.mapToDtoResponse(userSaved)).thenReturn(userExpected);

        UserDtoResponse userActual = service.save(userDtoRequest);

        assertEquals(userExpected, userActual);

        verify(repository).save(userToSave);

    }

    @Test
    public void saveUserAlreadyExist() {
        User userExist = new User();
        userExist.setId(1L);
        userExist.setName("testName");
        userExist.setLastname("testLastname");
        userExist.setEmail("mail@acme.com");
        userExist.setPassword("HashedTestPassword");
        userExist.setEnabled(true);
        userExist.setLocked(false);
        userExist.setRole(UserRole.USER);

        when(repository.findByEmailIgnoreCase("mail@acme.com")).thenReturn(Optional.of(userExist));

        UserDtoRequest userDtoRequest = new UserDtoRequest(
                "testName", "tesLastname", "mail@acme.com", "HashedTestPassword");

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> service.save(userDtoRequest));
        assertEquals("400 BAD_REQUEST \"User exist!\"", e.getMessage());

        verify(repository, never()).save(any());
    }

    //search by username found/ not found

    @Test
    public void loadByUsernameFound() {
        User user = new User();
        user.setId(1L);
        user.setName("testName");
        user.setLastname("testLastname");
        user.setEmail("mail@acme.com");
        user.setPassword("HashedTestPassword");
        user.setEnabled(true);
        user.setLocked(false);
        user.setRole(UserRole.USER);

        when(repository.findByEmailIgnoreCase("mail@acme.com")).thenReturn(Optional.of(user));

        UserDetails userActual = service.loadUserByUsername("mail@acme.com");

        assertEquals(user, userActual);

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsernameNotFound() {
        when(repository.findByEmailIgnoreCase("test")).thenReturn(Optional.empty());
        service.loadUserByUsername("test");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsernameNullArgument() {
        service.loadUserByUsername(null);
    }
}