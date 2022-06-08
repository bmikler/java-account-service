package account.user;

import account.security.loggin.LoggingService;
import account.security.password.PasswordRequest;
import account.security.password.PasswordResponse;
import account.user.model.User;
import account.user.model.dto.UserDtoMapper;
import account.user.model.dto.UserDtoRequest;
import account.user.model.dto.UserDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final LoggingService loggingService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserDtoRequest user, HttpServletRequest request) {
        UserDtoResponse userSaved = userService.save(user);

        loggingService.userCreated(userSaved.getEmail(), request.getRequestURI());

        return ResponseEntity.ok(userSaved);
    }

    @PostMapping("/auth/changepass")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal User user,
                                            @RequestBody @Valid PasswordRequest passwordRequest) {

        PasswordResponse passwordResponse = userService.updatePassword(user, passwordRequest.getPassword());
        return ResponseEntity.ok(passwordResponse);
    }

}
