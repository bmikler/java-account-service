package account.payment.model.dto;

import account.payment.model.Payment;
import account.user.model.User;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PaymentDtoMapper {

    public Payment map (PaymentDtoRequest paymentDtoRequest, User user) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPeriod(convertStringToDate(paymentDtoRequest.getPeriod()));
        payment.setSalary(paymentDtoRequest.getSalary());

        return payment;
    }

    public PaymentDtoResponse map(Payment payment){
        return new PaymentDtoResponse(
                payment.getUser().getName(),
                payment.getUser().getLastname(),
                convertDateToString(payment.getPeriod()),
                convertLongToSalary(payment.getSalary())
        );
    }

    private String convertLongToSalary(long salary){

        long dollars = salary / 100;

        return dollars + " dollar(s) " + (salary - (dollars * 100)) + " cent(s)";
    }
    private LocalDate convertStringToDate(String date) {

        return LocalDate.parse("01-" + date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    }

    private String convertDateToString(LocalDate date) {
        return  date.format(DateTimeFormatter.ofPattern("LLLL-yyyy", Locale.ENGLISH));
    }
}
