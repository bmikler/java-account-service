package account.payment;

import account.payment.model.Payment;
import account.payment.util.PaymentDtoMapper;
import account.payment.model.dto.PaymentDtoRequest;
import account.payment.model.dto.PaymentDtoResponse;
import account.user.UserRepository;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static account.payment.util.DateConverter.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentDtoMapper mapper;

    public Optional<PaymentDtoResponse> getPaymentByPeriod(User user, String period) {
        return paymentRepository
                .findPaymentByUserAndPeriod(user, stringToDate(period))
                .map(mapper::map);

    }

    public List<PaymentDtoResponse> getAllPaymentsByUser(User user) {

        return paymentRepository.findPaymentByUserOrderByPeriodDesc(user)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

    }

    @Transactional
    public void addPaymentsList(List<PaymentDtoRequest> payments) {

        payments.forEach(this::addPayment);
    }

    private void addPayment(PaymentDtoRequest payment){

        User user = findUser(payment.getEmployee());

        Payment paymentToSave = mapper.map(payment, user);

        if(isPaymentExist(paymentToSave)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This payment already exist!");
        }

        paymentRepository.save(paymentToSave);
    }

    private boolean isPaymentExist(Payment payment) {
        return paymentRepository
                .findPaymentByUserAndPeriod(payment.getUser(), payment.getPeriod())
                .isPresent();
    }

    private User findUser(String email) {
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User " + email + " not found!"));
    }

    public void updatePayment(PaymentDtoRequest paymentDtoRequest) {

        User user = findUser(paymentDtoRequest.getEmployee());

        Payment payment = paymentRepository
                .findPaymentByUserAndPeriod(user, stringToDate(paymentDtoRequest.getPeriod()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not found"));

        payment.setSalary(paymentDtoRequest.getSalary());
        paymentRepository.save(payment);
    }


}
