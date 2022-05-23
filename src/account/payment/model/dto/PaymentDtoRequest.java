package account.payment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDtoRequest {

    @NotBlank
    private String employee;
    @NotNull
    private String period;
    @Min(value = 1, message = "Salary must by positive number!")
    private long salary;

}
