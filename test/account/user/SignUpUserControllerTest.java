//package account.user;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.*;
//import static org.springframework.http.MediaType.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class SignUpUserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void signUpOK() throws Exception {
//
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"email\":\"tester@acme.com\"," +
//                " \"password\":\"superTurboPassword\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                .content(inputJson)
//                .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is("tester")))
//                .andExpect(jsonPath("$.lastname", is("testerSurname")))
//                .andExpect(jsonPath("$.email", is("tester@acme.com")));
//
//    }
//
//    @Test
//    public void signUpUserAlreadyExist () throws Exception {
//
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"email\":\"testerAlreadyExist@acme.com\"," +
//                " \"password\":\"superTurboPassword\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(status().reason("User exist!"));
//
//    }
//
//    @Test
//    public void signUpPasswordInHackersDatabase() throws Exception {
//
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"email\":\"passwordInHackersDatabase@acme.com\"," +
//                " \"password\":\"PasswordForJanuary\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("The password is in the hacker's database!")));
//
//    }
//
//    @Test
//    public void signUpPasswordToShort() throws Exception {
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"email\":\"passwordToShort@acme.com\"," +
//                " \"password\":\"xy\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("The password length must be at least 12 chars!")));
//
//    }
//
//    @Test
//    public void signUpEmailFromWrongDomain() throws Exception {
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"email\":\"emailFromWrongDomain@gmail.com\"," +
//                " \"password\":\"superTurboPassword\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("Email must be registered in acme.com domain")));
//
//    }
//
//    @Test
//    public void signUpNoName() throws Exception {
//        String inputJson = "{\"lastname\": \"testerSurname\"," +
//                " \"email\":\"emailFromWrongDomain@acme.com\"," +
//                " \"password\":\"superTurboPassword\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("must not be blank")));
//
//    }
//
//    @Test
//    public void signUpNoLastName() throws Exception {
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"email\":\"emailFromWrongDomain@acme.com\"," +
//                " \"password\":\"superTurboPassword\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("must not be blank")));
//
//    }
//
//    @Test
//    public void signUpNoEmail() throws Exception {
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"password\":\"superTurboPassword\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("must not be blank")));
//
//    }
//
//    @Test
//    public void signUpNoPassword() throws Exception {
//        String inputJson = "{\"name\":\"tester\"," +
//                " \"lastname\": \"testerSurname\"," +
//                " \"email\":\"emailFromWrongDomain@acme.com\"}";
//
//        mockMvc.perform(post("/api/auth/signup")
//                        .content(inputJson)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message", is("must not be blank")));
//
//    }
//
//}