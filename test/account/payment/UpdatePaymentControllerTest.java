package account.payment;

import account.user.UserRepository;
import account.user.model.User;
import account.user.model.UserRole;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdatePaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    @Transactional
//    public void updatePaymentOK() throws Exception {
//        User user = userRepository.save(new User("name", "lastname",
//                "a@acme.com", passwordEncoder.encode("passwrod12345"),
//                UserRole.USER, new ArrayList<>(), true, false));
//
//        userRepository.save(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/acct/payments"))
//
//
//    }

    //TODO - payment controller update test
    //ok
    //user don`t exist
    //payment don`t exist
    //negative sallary

}