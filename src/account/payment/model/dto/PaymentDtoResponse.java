package account.payment.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDtoResponse {
    private String name;
    private String lastname;
    private String period;
    private String salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDtoResponse that = (PaymentDtoResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(lastname, that.lastname) && Objects.equals(period, that.period) && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastname, period, salary);
    }
}
