package account.admin;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AdminController {

    @PutMapping("admin/user/role")
    public ResponseEntity<?> setRoles(){
        throw new RuntimeException("Not implemented yet");
    }

    @DeleteMapping("api/admin/user")
    public ResponseEntity<?> deleteUser() {
        throw new RuntimeException("Not implemented yet");
    }

    @GetMapping("api/admin/user")
    public ResponseEntity<?> getInfoAboutUsers() {
        throw new RuntimeException("Not implemented yet");
    }

}
