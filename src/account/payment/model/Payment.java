package account.payment.model;

import account.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Objects;


@Entity
@Getter
@ToString
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private YearMonth period;
    private long salary;
    @ManyToOne
    private User user;

    public Payment(YearMonth period, long salary, User user) {
        this.period = period;
        this.salary = salary;
        this.user = user;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id == payment.id && salary == payment.salary && Objects.equals(period, payment.period) && Objects.equals(user, payment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, period, salary, user);
    }
}
