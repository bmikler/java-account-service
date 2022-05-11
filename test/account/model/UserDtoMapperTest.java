package account.model;

import account.user.model.UserRole;
import account.user.model.User;
import account.user.model.dto.UserDtoMapper;
import account.user.model.dto.UserDtoRequest;
import account.user.model.dto.UserDtoResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDtoMapperTest {

    private UserDtoMapper mapper;

    @Before
    public void init() {
        mapper = new UserDtoMapper();
    }

    //map to user
    @Test
    public void mapToUserOK() {

        UserDtoRequest userDtoRequest = new UserDtoRequest
                ("testName", "testLastname", "mail@acme.com", "HashedTestPassword");


        User userExpected = new User();
        userExpected.setName("testName");
        userExpected.setLastname("testLastname");
        userExpected.setEmail("mail@acme.com");
        userExpected.setPassword("HashedTestPassword");
        userExpected.setEnabled(true);
        userExpected.setLocked(false);
        userExpected.setRole(UserRole.USER);

        User userActual = mapper.mapToUser(userDtoRequest);

        Assert.assertEquals(userExpected, userActual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mapToUserNullArgument() {
        mapper.mapToUser(null);
    }

    //map to dtoResponse
    @Test
    public void mapToDtoResponseOK(){
        User user = new User();
        user.setId(1L);
        user.setName("testName");
        user.setLastname("testLastname");
        user.setEmail("mail@acme.com");
        user.setPassword("HashedTestPassword");
        user.setEnabled(true);
        user.setLocked(false);
        user.setRole(UserRole.USER);

        UserDtoResponse userDtoResponseExpected = new UserDtoResponse(
                1L, "testName", "testLastname", "mail@acme.com");
        UserDtoResponse userDtoResponseActual = mapper.mapToDtoResponse(user);

        assertEquals(userDtoResponseExpected, userDtoResponseActual);

    }

    @Test(expected = IllegalArgumentException.class)
    public void mapToDtoResponseNullArgument() {
        mapper.mapToDtoResponse(null);
    }

}