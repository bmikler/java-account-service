package account.security.loggin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventsController {

    private final EventsRepository eventsRepository;

    @GetMapping("/security/events")
    public List<EventEntity> getEvents() {
        return eventsRepository.findAll();
    }

}
