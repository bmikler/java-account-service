package account.payment;

import account.payment.model.Response;
import account.payment.model.dto.PaymentDtoRequest;
import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/empl/payment")
    public ResponseEntity<?> getPayroll(@AuthenticationPrincipal User user,
                                        @RequestParam(required = false)
                                        @Pattern(regexp = "(0?[1-9]|1[012])-((?:19|20)[0-9][0-9])", message = "Wrong date!")
                                                String period) {

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
