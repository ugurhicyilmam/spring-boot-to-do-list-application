package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeControllerTest {

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
        testUser.setUsername("homeTestUser");
        testUser.setPassword(passwordEncoder.encode("password"));
        userRepository.save(testUser);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(testUser);
    }

    @Test
    public void index_shouldReturnHomeIfNotLoggedIn() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(model().attributeDoesNotExist("user")) // should not exist
                .andExpect(model().attributeDoesNotExist("todoList")); // should not exist
    }

    @Test
    public void index_shouldHaveTodoList() throws Exception {
        mockMvc.perform(get("/")
                .with(user(testUser)))
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("todoList")); // should exist since user logged in
    }

}