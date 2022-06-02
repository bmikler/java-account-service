package account.payment;

import account.payment.model.Payment;
import account.payment.model.dto.PaymentDtoRequest;
import account.payment.model.dto.PaymentDtoResponse;
import account.user.UserRepository;
import account.user.model.User;
import account.user.model.UserRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.persistence.Access;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getPaymentUnauthorizedUserWithoutPeriod() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/empl/payment"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void getPaymentUnauthorizedUserWithPeriod() throws Exception {

        String inputJson = "{\"period\":\"01-2022\"}";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/empl/payment")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    public void getPaymentWithoutPeriodOK() throws Exception {

        User user = userRepository.save(new User("name", "lastname",
                "a@acme.com", passwordEncoder.encode("passwrod12345"),
                UserRole.USER, new ArrayList<>(), true, false));

        paymentRepository.save(new Payment(YearMonth.of(2022, 1), 1000000, user));
        paymentRepository.save(new Payment(YearMonth.of(2022, 2), 1000000, user));

        MvcResult mvcResult = mockMvc.perform(get("/api/empl/payment")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<PaymentDtoResponse>> type = new TypeReference<>() {};

        List<PaymentDtoResponse> actualResponseList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);

        List<PaymentDtoResponse> expectedResponseList = List.of(new PaymentDtoResponse("name", "lastname", "February-2022", "10000 dollar(s) 0 cent(s)"),
                new PaymentDtoResponse("name", "lastname", "January-2022", "10000 dollar(s) 0 cent(s)"));

        assertEquals(2, actualResponseList.size());
        assertEquals(expectedResponseList, actualResponseList);

    }

    @Test
    @Transactional
    public void getPaymentWithPeriodOK() throws Exception {

        User user = userRepository.save(new User("name", "lastname",
                "a@acme.com", passwordEncoder.encode("passwrod12345"),
                UserRole.USER, new ArrayList<>(), true, false));

        paymentRepository.save(new Payment(YearMonth.of(2022, 1), 1000000, user));
        paymentRepository.save(new Payment(YearMonth.of(2022, 2), 1000000, user));

        MvcResult mvcResult = mockMvc.perform(get("/api/empl/payment?period=01-2022")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        PaymentDtoResponse paymentResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PaymentDtoResponse.class);

        Assert.assertEquals("name", paymentResponse.getName());
        Assert.assertEquals("lastname", paymentResponse.getLastname());
        Assert.assertEquals("January-2022", paymentResponse.getPeriod());
        Assert.assertEquals("10000 dollar(s) 0 cent(s)", paymentResponse.getSalary());

    }


    @Test
    @Transactional
    public void getPaymentsWrongPeriodFormat() throws Exception {

        User user = userRepository.save(new User("name", "lastname",
                "a@acme.com", passwordEncoder.encode("passwrod12345"),
                UserRole.USER, new ArrayList<>(), true, false));

        MvcResult mvcResult = mockMvc.perform(get("/api/empl/payment?period=13-2022")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("getPayroll.period: Wrong date!")))
                .andReturn();

    }

    @Test
    @Transactional
    public void getPaymentsPaymentNotFound() throws Exception {

        User user = userRepository.save(new User("name", "lastname",
                "a@acme.com", passwordEncoder.encode("passwrod12345"),
                UserRole.USER, new ArrayList<>(), true, false));

        MvcResult mvcResult = mockMvc.perform(get("/api/empl/payment?period=12-2022")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

    }



}