package com.ugurhicyilmam.service;

import com.ugurhicyilmam.controller.request.RegistrationRequest;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void register_shouldSetInfoCorrectly() throws Exception {
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        RegistrationRequest req = new RegistrationRequest();
        req.setUsername("john");
        req.setPassword("password");
        User user = userService.register(req);

        Assert.assertEquals(req.getUsername(), user.getUsername());
        Assert.assertEquals("encodedPassword", user.getPassword());
        Assert.assertEquals(true, user.isEnabled());
        Assert.assertEquals(true, user.isAccountNonExpired());
        Assert.assertEquals(true, user.isAccountNonLocked());
        Assert.assertEquals(true, user.isCredentialsNonExpired());
        Assert.assertTrue(user.getAuthorities().isEmpty()); // we don't do role based authentication
    }

    @Test
    public void findByUsername_shouldReturnIfUserFound() throws Exception {
        User user = new User();
        when(userRepository.findByUsername("john")).thenReturn(user);

        User returnedUser = userService.findByUsername("john");

        Assert.assertEquals(user, returnedUser);
    }

    @Test
    public void loadUserByUsername_shouldThrowExceptionIfNotFound() throws Exception {
        when(userRepository.findByUsername("john")).thenReturn(null);

        try {
            userService.loadUserByUsername("john");
        } catch (UsernameNotFoundException ex) {
            return;
        }

        Assert.assertTrue(false);
    }

    @Test
    public void loadUserByUsername_shouldReturnIfUserFound() throws Exception {
        User user = new User();
        when(userRepository.findByUsername("john")).thenReturn(user);

        UserDetails returnedUser = userService.loadUserByUsername("john");

        Assert.assertEquals(user, returnedUser);
    }
}