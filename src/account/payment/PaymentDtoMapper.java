package account.payment;

import account.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class PaymentDtoMapper {

    public Payment map (PaymentDtoRequest paymentDtoRequest, User user) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPeriod(paymentDtoRequest.getPeriod());
        payment.setSalary(paymentDtoRequest.getSalary());

        return payment;
    }

    public PaymentDtoResponse map(Payment payment){
        return new PaymentDtoResponse(
                payment.getUser().getName(),
                payment.getUser().getLastname(),
                payment.getPeriod().toString(),
                convertLongToSalary(payment.getSalary())
        );
    }

    private String convertLongToSalary(long salary){
        return salary + " dollar(s)";
    }

}
