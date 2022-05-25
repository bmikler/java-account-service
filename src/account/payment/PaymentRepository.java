package account.payment;

import account.payment.model.Payment;
import account.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentByUserOrderByPeriodDesc(User user);

    Optional<Payment> findPaymentByUserAndPeriod(User user, YearMonth period);

}
