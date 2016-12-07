package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class TodoController {

    private final UserService userService;

    @Autowired
    public TodoController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public String index(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "todo";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public String indexGet(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "todo";
    }
}
