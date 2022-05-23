package account.payment;

import account.payment.model.Payment;
import account.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findPaymentByUser(User user);

    public Optional<Payment> findPaymentByUserAndPeriod(User user, String period);

}
