package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.TodoService;
import com.ugurhicyilmam.service.UserService;
import com.ugurhicyilmam.transfer.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;
    private final TodoService todoService;

    @Autowired
    public HomeController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal, Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        User user = null;
        if (principal != null)
            user = userService.findByUsername(principal.getName());
        if (user == null)
            return "home";
        //add user's to-dos
        model.addAttribute("user", new UserTransfer(user));

        page = (page == null) ? 0 : page;
        limit = (limit == null) ? 10 : limit;

        model.addAttribute("todoList", todoService.findTodos(page, limit, user));
        
        return "home";
    }


}
