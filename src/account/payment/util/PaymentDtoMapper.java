package account.payment.util;

import account.payment.model.Payment;
import account.payment.model.dto.PaymentDtoRequest;
import account.payment.model.dto.PaymentDtoResponse;
import account.payment.util.DateConverter;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static account.payment.util.DateConverter.*;

@Service
public class PaymentDtoMapper {

    public Payment map (PaymentDtoRequest paymentDtoRequest, User user) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPeriod(stringToDate(paymentDtoRequest.getPeriod()));
        payment.setSalary(paymentDtoRequest.getSalary());

        return payment;
    }

    public PaymentDtoResponse map(Payment payment){
        return new PaymentDtoResponse(
                payment.getUser().getName(),
                payment.getUser().getLastname(),
                dateToString(payment.getPeriod()),
                convertLongToSalary(payment.getSalary())
        );
    }

    private String convertLongToSalary(long salary){

        long dollars = salary / 100;

        return dollars + " dollar(s) " + (salary - (dollars * 100)) + " cent(s)";
    }

}
