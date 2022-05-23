package account.payment.model.dto;

import account.payment.model.Payment;
import account.user.model.User;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentDtoMapper {

    public Payment map (PaymentDtoRequest paymentDtoRequest, User user) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPeriod(LocalDate.parse("01-" + paymentDtoRequest.getPeriod(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        payment.setSalary(paymentDtoRequest.getSalary());

        return payment;
    }

    public PaymentDtoResponse map(Payment payment){
        return new PaymentDtoResponse(
                payment.getUser().getName(),
                payment.getUser().getLastname(),
                payment.getPeriod(),
                convertLongToSalary(payment.getSalary())
        );
    }

    private String convertLongToSalary(long salary){
        return salary + " dollar(s)";
    }
    private LocalDate convertStringToDate(String date) {

        DateFormatter formatter = new DateFormatter();

        return LocalDate.parse(date);

    }
}
