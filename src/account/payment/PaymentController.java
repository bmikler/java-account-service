package account.payment;

import account.payment.model.Response;
import account.payment.model.dto.PaymentDtoRequest;
import account.payment.model.dto.PaymentDtoResponse;
import account.user.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/empl/payment")
    public ResponseEntity<?> getPayroll(@AuthenticationPrincipal User user,
                                               @RequestBody(required = false) String period) {
        if (period != null) {
            return ResponseEntity.of(paymentService.getPaymentByPeriod(user, period));
        } else {
            return ResponseEntity.ok(paymentService.getAllPaymentsByUser(user));
        }

    }

    @PostMapping("/acct/payments")
    public ResponseEntity<?> uploadPayments(@RequestBody List<@Valid PaymentDtoRequest> payments) {

        paymentService.addPaymentsList(payments);
        return ResponseEntity.ok(new Response("Added successfully!"));
    }

    @PutMapping("/acct/payments")
    public ResponseEntity<?> updatePayment(@RequestBody @Valid PaymentDtoRequest paymentDtoRequest) {
        paymentService.updatePayment(paymentDtoRequest);

        return ResponseEntity.ok(new Response("Updated successfully!"));
    }

}
