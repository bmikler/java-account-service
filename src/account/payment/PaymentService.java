package account.payment;

import account.payment.model.Payment;
import account.payment.model.dto.PaymentDtoMapper;
import account.payment.model.dto.PaymentDtoRequest;
import account.payment.model.dto.PaymentDtoResponse;
import account.payment.util.DateConverter;
import account.user.UserRepository;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.Date;
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

    @Transactional
    public void addPaymentsList(List<PaymentDtoRequest> payments) {

        payments.forEach(p -> {
            User user = userRepository
                    .findByEmailIgnoreCase(p.getEmployee())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User " + p.getEmployee() + " not found!"));

            Payment paymentToSave = mapper.map(p, user);

            paymentRepository
                    .findPaymentByUserAndPeriod(user, paymentToSave.getPeriod())
                    .ifPresent(status -> {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment for " + p.getPeriod() + " for " + user.getName() + " is already saved");
                    }
            );

            paymentRepository.save(paymentToSave);

        });
    }

    public List<PaymentDtoResponse> getAllPaymentsByUser(User user) {

        return paymentRepository.findPaymentByUserOrderByPeriodDesc(user)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());

    }

    public void updatePayment(PaymentDtoRequest paymentDtoRequest) {

        User user = userRepository
                .findByEmailIgnoreCase(paymentDtoRequest.getEmployee())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        Payment payment = paymentRepository
                .findPaymentByUserAndPeriod(user, stringToDate(paymentDtoRequest.getPeriod()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment not found"));

        payment.setSalary(paymentDtoRequest.getSalary());
        paymentRepository.save(payment);
    }

    public Optional<PaymentDtoResponse> getPaymentByPeriod(User user, String period) {
        return paymentRepository
                .findPaymentByUserAndPeriod(user, stringToDate(period))
                .map(mapper::map);

    }


}
