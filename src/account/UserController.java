package account;

import account.model.User;
import account.model.UserDtoMapper;
import account.model.UserDtoRequest;
import account.model.UserDtoResponse;
import account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static account.model.UserDtoMapper.*;

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

}
