package account.payment;

import account.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findPaymentByUser(User user);

}
