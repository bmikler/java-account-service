package account.payment;

import account.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/empl/payment")
    public List<PaymentDtoResponse> getPayroll(@AuthenticationPrincipal User user) {

        return paymentService.getAllPaymentsByUser(user);

    }

    @PostMapping("/acct/payments")
    public ResponseEntity<?> uploadPayments(@RequestBody List<@Valid PaymentDtoRequest> payments) {
        paymentService.addPaymentsList(payments);

        return ResponseEntity.ok("Updated successfully!");
    }

}
