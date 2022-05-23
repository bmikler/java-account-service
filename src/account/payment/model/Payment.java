package account.payment.model;

import account.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.core.instrument.Meter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate period;
    private long salary;
    @ManyToOne
    private User user;
}
