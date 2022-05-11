package account.payment;

import account.user.UserRepository;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

            //TODO - validation if there is no duplicate in period

            Payment paymentToSave = mapper.map(p, user);

            paymentRepository.save(paymentToSave);

        });
    }

    public List<PaymentDtoResponse> getAllPaymentsByUser(User user) {

        return paymentRepository.findPaymentByUser(user)
                .stream()
                .map(mapper::map)
                .toList();

    }

}
