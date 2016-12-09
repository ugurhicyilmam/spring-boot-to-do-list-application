package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.TodoService;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

    @Value("${app.todo.page-limit}")
    private int limit;

    @Autowired
    public HomeController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal, Model model, @RequestParam(required = false) Integer page) {
        User user = null;
        if (principal != null)
            user = userService.findByUsername(principal.getName());
        if (user == null)
            return "home";

        //add user's to-dos
        int currentPage = (page == null) ? 0 : page;
        Page<Todo> todoList = todoService.findTodos(currentPage, limit, user);

        model.addAttribute("todoList", todoList);

        return "home";
    }


}
