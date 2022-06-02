package account.payment;

import account.payment.model.Payment;
import account.payment.model.dto.PaymentDtoRequest;
import account.user.UserRepository;
import account.user.model.User;
import account.user.model.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UploadPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        userRepository.save(new User(1L, "name", "lastname", "a@acme.com", passwordEncoder.encode("passwrod12345"), UserRole.USER, new ArrayList<>(), true, false));
        userRepository.save(new User(2L, "name2", "lastname2", "b@acme.com", passwordEncoder.encode("passwrod12345"), UserRole.USER, new ArrayList<>(), true, false));
    }

    //TODO payment controller accountant authorization

    @Test
    public void uploadPaymentsOK() throws Exception {

        int dbSizeBefore = paymentRepository.findAll().size();

        List<PaymentDtoRequest> payments = List.of(new PaymentDtoRequest("a@acme.com", "01-2022", 10000L),
                new PaymentDtoRequest("a@acme.com", "02-2022", 100000L));

        String jsonInput = objectMapper.writeValueAsString(payments);

        mockMvc.perform(post("/api/acct/payments")
                    .content(jsonInput)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("Added successfully!")));

        int dbSizeAfter = paymentRepository.findAll().size();

        assertEquals(dbSizeAfter, dbSizeBefore + 2);

    }

    // user not found
    @Test
    public void uploadPaymentsUserNotFound() throws Exception {

        int dbSizeBefore = paymentRepository.findAll().size();

        List<PaymentDtoRequest> payments = List.of(new PaymentDtoRequest("a@acme.com", "03-2022", 10000L),
                new PaymentDtoRequest("c@acme.com", "04-2022", 100000L));

        String jsonInput = objectMapper.writeValueAsString(payments);

        mockMvc.perform(post("/api/acct/payments")
                .content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User c@acme.com not found!"));

        int dbSizeAfter = paymentRepository.findAll().size();

        assertEquals(dbSizeAfter, dbSizeBefore);

    }


    @Test
    public void uploadPaymentsDuplicateInTheList() throws Exception {

        int dbSizeBefore = paymentRepository.findAll().size();

        List<PaymentDtoRequest> payments = List.of(new PaymentDtoRequest("a@acme.com", "05-2022", 10000L),
                new PaymentDtoRequest("a@acme.com", "05-2022", 100000L));

        String jsonInput = objectMapper.writeValueAsString(payments);

        mockMvc.perform(post("/api/acct/payments")
                        .content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This payment already exist!"));

        int dbSizeAfter = paymentRepository.findAll().size();

        assertEquals(dbSizeAfter, dbSizeBefore);

    }

    @Test
    public void uploadPaymentsAlreadyExistInDB() throws Exception {

        User user = userRepository.findByEmailIgnoreCase("a@acme.com").get();
        paymentRepository.save(new Payment(YearMonth.of(2022,06), 5000000, user));

        int dbSizeBefore = paymentRepository.findAll().size();

        List<PaymentDtoRequest> payments = List.of(new PaymentDtoRequest("a@acme.com", "07-2022", 10000L),
                new PaymentDtoRequest("a@acme.com", "06-2022", 100000L));

        String jsonInput = objectMapper.writeValueAsString(payments);

        mockMvc.perform(post("/api/acct/payments")
                        .content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("This payment already exist!"));

        int dbSizeAfter = paymentRepository.findAll().size();

        assertEquals(dbSizeAfter, dbSizeBefore);

    }

    @Test
    public void uploadPaymentNegativeSalary() throws Exception {

        int dbSizeBefore = paymentRepository.findAll().size();

        List<PaymentDtoRequest> payments = List.of(new PaymentDtoRequest("a@acme.com", "08-2022", 10000L),
                new PaymentDtoRequest("a@acme.com", "09-2022", -100000L));

        String jsonInput = objectMapper.writeValueAsString(payments);

        mockMvc.perform(post("/api/acct/payments")
                        .content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("uploadPayments.payments[1].salary: Salary must by positive number!")));

        int dbSizeAfter = paymentRepository.findAll().size();

        assertEquals(dbSizeAfter, dbSizeBefore);


    }

    @Test
    public void uploadPaymentWrongDateFormat() throws Exception {

        int dbSizeBefore = paymentRepository.findAll().size();

        List<PaymentDtoRequest> payments = List.of(new PaymentDtoRequest("a@acme.com", "08-2022", 10000L),
                new PaymentDtoRequest("a@acme.com", "abc", 100000L));

        String jsonInput = objectMapper.writeValueAsString(payments);

        mockMvc.perform(post("/api/acct/payments")
                        .content(jsonInput).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("uploadPayments.payments[1].period: Wrong date!")));

        int dbSizeAfter = paymentRepository.findAll().size();

        assertEquals(dbSizeAfter, dbSizeBefore);


    }

}