package account.payment;

import account.user.model.User;
import account.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentDtoMapperTest {

    private PaymentDtoMapper paymentDtoMapper;

    @Before
    public void init() {
        paymentDtoMapper = new PaymentDtoMapper();
    }

    @Test
    public void map() {
        User user = new User(1L, "test", "test", "test@acme.com",
                "password", UserRole.USER, new ArrayList<>(), true, false);

        PaymentDtoRequest paymentDtoRequest = new PaymentDtoRequest("test@acme.com",
                LocalDate.of(2022, 01, 01), 10000);

        Payment paymentExpected = new Payment();
        paymentExpected.setPeriod(LocalDate.of(2022, 01, 01));
        paymentExpected.setSalary(10000);
        paymentExpected.setUser(user);

        Payment paymentActual = paymentDtoMapper.map(paymentDtoRequest, user);

        Assertions.assertEquals(paymentExpected, paymentActual);

    }

}