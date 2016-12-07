package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal, Model model) {
        User user = null;
        if (principal != null)
            user = userService.findByUsername(principal.getName());
        if (user == null)
            return "home";
        model.addAttribute("name", user.getUsername());
        return "home";
    }


}
