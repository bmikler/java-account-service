package account.security.loggin;

import account.admin.AdminOperation;
import account.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static account.admin.AdminOperation.*;
import static account.security.loggin.EventType.*;

@Service
@RequiredArgsConstructor
public class LoggingService {

    private final EventsRepository eventsRepository;

    public void userCreated(String email, String path) {
        EventEntity event = new EventEntity(CREATE_USER, email, path);
        eventsRepository.save(event);
    }

    public void roleChanged(String admin, String user, String path, UserRole role, AdminOperation operation) {

        EventType action = null;
        String object = null;

        if (operation == GRANT){
            action = GRANT_ROLE;
            object = "Grant role " + role.name() + " to " + user;
        }

        if (operation == REMOVE) {
            action = REMOVE_ROLE;
            object = "Remove role " + role.name() + " from " + user;
        }

        EventEntity event = new EventEntity(action, admin, object, path);
        eventsRepository.save(event);

    }




}
