package account;

import account.model.UserDtoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static account.model.UserDtoMapper.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserDtoRequest user) {
        return ResponseEntity.ok(mapToDtoResponse(user));
    }

}
