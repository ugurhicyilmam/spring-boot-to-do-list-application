package com.ugurhicyilmam.validation;

import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsernameNotTakenValidatorTest {

    @MockBean
    private UserService userService;

    @Test
    public void isValid_shouldReturnFalseIfUserExists() throws Exception {
        given(this.userService.findByUsername("john.doe"))
                .willReturn(new User());
        UsernameNotTakenValidator validator = new UsernameNotTakenValidator(userService);

        Assert.assertFalse(validator.isValid("john.doe", null));
    }

    @Test
    public void isValid_shouldReturnTrueIfUserNotFound() throws Exception {
        given(this.userService.findByUsername("john.doe"))
                .willReturn(null);
        UsernameNotTakenValidator validator = new UsernameNotTakenValidator(userService);

        Assert.assertTrue(validator.isValid("john.doe", null));
    }

}