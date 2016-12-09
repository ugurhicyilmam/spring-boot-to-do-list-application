package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.TodoRepository;
import com.ugurhicyilmam.repository.UserRepository;
import com.ugurhicyilmam.service.TodoService;
import org.hamcrest.core.StringContains;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoControllerTest {

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

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
        testUser.setUsername("todoTestUser");
        testUser.setPassword(passwordEncoder.encode("password"));
        userRepository.save(testUser);
    }

    @After
    public void tearDown() throws Exception {
        todoRepository.deleteAll();
        userRepository.delete(testUser);
    }

    @Test
    public void create_shouldReturnTodoCreate() throws Exception {
        mockMvc.perform(get("/todo/create")
                .with(user(testUser)))
                .andExpect(view().name("todo.create"));
    }

    @Test
    public void create_shouldRedirectLoginIfNotLoggedIn() throws Exception {
        mockMvc.perform(get("/todo/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", StringContains.containsString("/login"))); //since spring security redirect using full url
    }

    @Test
    public void delete_shouldRedirectHomeWhenUserLoggedIn() throws Exception {
        mockMvc.perform(get("/todo/1/delete")
                .with(user(testUser))) // user login)
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));
    }

    @Test
    public void delete_shouldRedirectLogin() throws Exception {
        mockMvc.perform(get("/todo/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", StringContains.containsString("/login"))); //since spring security redirect using full url
    }

    @Test
    public void create_shouldCreateTodo() throws Exception {
        mockMvc.perform(post("/todo")
                .param("todo", "todo")
                .with(user(testUser)) // user login
                .with(csrf()) // also need csrf token
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        //check if to-do correctly created
        Page<Todo> todoPage = todoService.findTodos(0, 10, testUser);
        Assert.assertNotNull(todoPage);
        Assert.assertNotNull(todoPage.getContent());
        Assert.assertNotNull(todoPage.getContent().get(0));
        Todo todo = todoPage.getContent().get(0);
        Assert.assertEquals("todo", todo.getContent());
        Assert.assertNotNull(todo.getCreatedAt());
    }

    @Test
    public void create_shouldValidateTodo() throws Exception {
        mockMvc.perform(post("/todo")
                .param("todo", "") // send empty to-do
                .with(user(testUser)) // user login
                .with(csrf()) // also need csrf token
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("todo.create"));
    }

    @Test
    public void create_shouldAuthenticate() throws Exception {
        mockMvc.perform(post("/todo")
                .param("todo", "todo") // send empty to-do
                .with(csrf()) // also need csrf token
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", StringContains.containsString("/login"))); //since spring security redirect using full url
    }
}