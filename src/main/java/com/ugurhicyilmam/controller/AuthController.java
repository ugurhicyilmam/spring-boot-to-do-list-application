package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.controller.request.RegistrationRequest;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Model model) {
        if (error != null) {
            System.out.println("error");
            model.addAttribute("loginError", "Invalid username or password");
        }

        if (logout != null) {
            model.addAttribute("logout", "Successfully logged out.");
        }

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(RegistrationRequest request) {
        User user = userService.register(request);
        System.out.println(user);
        return "register";
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
    public String loginSuccess(Principal principal) {
        if(principal != null)
            return "redirect:/";
        return "redirect:/login";

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }
}
