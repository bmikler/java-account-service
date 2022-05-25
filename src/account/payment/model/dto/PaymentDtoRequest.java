package account.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDtoRequest {

    @NotBlank
    private String employee;
    @NotNull
    @Pattern(regexp = "(0?[1-9]|1[012])-((?:19|20)[0-9][0-9])", message = "Wrong date!")
    private String period;
    @Min(value = 1, message = "Salary must by positive number!")
    private long salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDtoRequest that = (PaymentDtoRequest) o;
        return salary == that.salary && Objects.equals(employee, that.employee) && Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, period, salary);
    }

}
