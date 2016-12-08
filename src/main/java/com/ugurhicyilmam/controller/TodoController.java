package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.controller.request.TodoCreationRequest;
import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.TodoService;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "/todo/create", method = RequestMethod.GET)
    public String create() {
        return "todo.create";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/todo/{todoId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable long todoId) {
        todoService.deleteTodo(todoId);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public String create(Principal principal, TodoCreationRequest request) {
        User user = userService.findByUsername(principal.getName());
        todoService.createTodo(request.getTodo(), user);
        System.out.println("todo created");
        return "redirect:/";
    }

}
