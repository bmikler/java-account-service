package account.payment;

import account.user.UserRepository;
import account.user.UserService;
import account.user.model.User;
import account.user.model.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    private PaymentService paymentService;
    private PaymentRepository paymentRepository;
    private UserRepository userRepository;
    private PaymentDtoMapper mapper;


    @Before
    public void init(){
        paymentRepository = mock(PaymentRepository.class);
        userRepository = mock(UserRepository.class);
        mapper = mock(PaymentDtoMapper.class);
        paymentService = new PaymentService(paymentRepository, userRepository, mapper);
    }

    @Test
    public void addPaymentOK(){
        User user = new User(1L, "testName", "testLastName",
                "test@acme.com", "hashedPassword", UserRole.USER, new ArrayList<>(),true, false);

        when(userRepository.findByEmailIgnoreCase("test@acme.com")).thenReturn(Optional.of(user));

        PaymentDtoRequest paymentDtoRequest1 = new PaymentDtoRequest("test@acme.com",
                LocalDate.of(2022,01,01), 10000);
        PaymentDtoRequest paymentDtoRequest2 = new PaymentDtoRequest("test@acme.com",
                LocalDate.of(2022,02,01), 10000);

        List<PaymentDtoRequest> payments = List.of(paymentDtoRequest1, paymentDtoRequest2);

        Payment payment1 = new Payment();
        payment1.setUser(user);
        payment1.setPeriod(LocalDate.of(2022,01,01));
        payment1.setSalary(10000);

        Payment payment2 = new Payment();
        payment1.setUser(user);
        payment1.setPeriod(LocalDate.of(2022,02,01));
        payment1.setSalary(10000);


        when(mapper.map(paymentDtoRequest1, user)).thenReturn(payment1);
        when(mapper.map(paymentDtoRequest2, user)).thenReturn(payment2);

        paymentService.addPaymentsList(payments);

        verify(paymentRepository, times(1)).save(payment1);
        verify(paymentRepository, times(1)).save(payment2);

    }

    @Test
    public void addPaymentUserNotExistException() {


        PaymentDtoRequest payment1 = new PaymentDtoRequest("noexist@acme.com", LocalDate.of(2022,01,01), 10000);
        PaymentDtoRequest payment2 = new PaymentDtoRequest("noexist@acme.com", LocalDate.of(2022,02,01), 10000);

        List<PaymentDtoRequest> payments = List.of(payment1, payment2);

        when(userRepository.findByEmailIgnoreCase("noexist@acme.com")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> paymentService.addPaymentsList(payments));

        assertEquals(ex.getRawStatusCode(), 400);
        assertEquals(ex.getReason(), "User noexist@acme.com not found!");

        verify(paymentRepository, never()).save(any());

    }



//    @Test
//    public void addPaymentDateDuplicateException() {
//
//        PaymentDtoRequest payment1 = new PaymentDtoRequest("test@acme.com", LocalDate.of(2022,01,01), 10000);
//        PaymentDtoRequest payment2 = new PaymentDtoRequest("test@acme.com", LocalDate.of(2022,01,01), 10000);
//
//        List<PaymentDtoRequest> payments = List.of(payment1, payment2);
//
//        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> paymentService.addPayments(payments));
//
//        assertEquals(ex.getRawStatusCode(), 400);
//        assertEquals(ex.getMessage(), "Duplicates dates in payments list!");
//
//        verify(paymentRepository, never()).save(any());
//
//    }

}