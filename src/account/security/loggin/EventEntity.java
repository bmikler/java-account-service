package account.security.loggin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private LocalDate timestamp;
    @Enumerated(EnumType.STRING)
    private EventType action;
    private String subject;
    private String object;
    private String path;

    public EventEntity(EventType action, String object, String path) {
        this(action, "Anonymous", object, path);
    }

    public EventEntity(EventType action, String subject, String object, String path) {
        timestamp = LocalDate.now();
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }
}
