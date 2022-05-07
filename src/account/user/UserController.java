package account.user;

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

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("/empl/payment")
    public ResponseEntity<?> getPayment(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userDtoMapper.mapToDtoResponse(user));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserDtoRequest user) {
        UserDtoResponse userSaved = userService.save(user);
        return ResponseEntity.ok(userSaved);
    }

    @PostMapping("/auth/changepass")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal User user,
                                            @RequestBody @Valid PasswordRequest passwordRequest) {

        PasswordResponse passwordResponse = userService.updatePassword(user, passwordRequest.getPassword());
        return ResponseEntity.ok(passwordResponse);
    }

}
