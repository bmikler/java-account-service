package account.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDtoResponse {
    private String name;
    private String lastname;
    private String period;
    private String salary;
}
