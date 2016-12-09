package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.controller.request.RegistrationRequest;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password");
        }

        if (logout != null) {
            model.addAttribute("logout", "Successfully logged out.");
        }

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(RegistrationRequest registrationRequest) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid RegistrationRequest registrationRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "register";
        }
        userService.register(registrationRequest);
        return "redirect:/";
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
    public String loginSuccess(Principal principal) {
        if(principal != null)
            return "redirect:/";
        return "redirect:/login?error";
    }
}
