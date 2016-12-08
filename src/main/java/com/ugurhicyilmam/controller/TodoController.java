package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.TodoService;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;

@Controller
public class TodoController {

    private final UserService userService;
    private final TodoService todoService;

    @Autowired
    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
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

    @ResponseBody
    @RequestMapping(value = "/createTestTodo", method = RequestMethod.GET)
    public void createTestTodo(Principal principal) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            Todo todo = new Todo();
            todo.setContent("Todo");
            todo.setUser(user);
            todo.setCreatedAt(new Date());
            todoService.save(todo);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getTestTodo", method = RequestMethod.GET)
    public Page<Todo> getTestTodo(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return todoService.findTodos(0, 10, user);
    }
}
