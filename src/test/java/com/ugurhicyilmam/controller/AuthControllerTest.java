package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    private User testUser;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .apply(springSecurity())
                .build();

        //create test user
        testUser = new User();
        testUser.setUsername("authTestUser");
        testUser.setPassword(passwordEncoder.encode("password"));
        userRepository.save(testUser);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(testUser);
    }

    @Test
    public void login_shouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void login_shouldReturnErrorIfExists() throws Exception {
        mockMvc.perform(get("/login?error=asd"))
                .andExpect(model().attributeExists("loginError"))
                .andExpect(model().attribute("loginError", is("Invalid username or password")));
    }

    @Test
    public void login_shouldReturnLogoutIfExists() throws Exception {
        mockMvc.perform(get("/login?logout=asd"))
                .andExpect(model().attributeExists("logout"))
                .andExpect(model().attribute("logout", is("Successfully logged out.")));
    }

    @Test
    public void register_shouldReturnRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(view().name("register"));
    }

    @Test
    public void register_shouldRegisterNewUser() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "johnDoe")
                .param("password", "password")
                .param("passwordConfirmation", "password")
                .with(csrf())) //we should also send a csrf token
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        //check if user really registered on database
        User user = userRepository.findByUsername("johnDoe");
        Assert.assertNotNull(user); // user is registered
        Assert.assertTrue(passwordEncoder.matches("password", user.getPassword())); // password is correctly set
    }

    @Test
    public void register_shouldValidatePasswordConfirmation() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "johnDoe")
                .param("password", "password")
                .param("passwordConfirmation", "wrong_password_confirmation") // send wrong password confirmation
                .with(csrf())) //we should also send a csrf token
                .andExpect(status().isOk()) // validation by default returns 200
                .andExpect(view().name("register")); //...but it returns back
    }

    @Test
    public void register_shouldValidateShortPassword() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "johnDoe")
                .param("password", "pass") // a short password
                .param("passwordConfirmation", "pass") // send wrong password confirmation
                .with(csrf())) //we should also send a csrf token
                .andExpect(status().isOk()) // validation by default returns 200
                .andExpect(view().name("register")); //...but it returns back
    }

    @Test
    public void register_shouldValidatUniqueUsername() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "authTestUser") // we already have a testUser, so registration should not accept it again
                .param("password", "password")
                .param("passwordConfirmation", "password")
                .with(csrf())) //we should also send a csrf token
                .andExpect(status().isOk()) // validation by default returns 200
                .andExpect(view().name("register")); //...but it returns back
    }


    @Test
    public void loginSuccess_shouldRedirectHomeIfUserLoggedIn() throws Exception {
        mockMvc.perform(post("/loginSuccess")
                .with(user(testUser)) // login with test user
                .with(csrf())) //since it is post, also need csrf
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void loginSuccess_shouldRedirectLoginIfNoUser() throws Exception {
        mockMvc.perform(post("/loginSuccess")
                .with(csrf())) //since it is post, also need csrf
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login?error"));
    }
}