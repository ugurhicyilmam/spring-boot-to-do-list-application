package com.ugurhicyilmam.controller;

import com.ugurhicyilmam.controller.request.TodoCreationRequest;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.service.TodoService;
import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

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
    public String create(TodoCreationRequest todoCreationRequest) {
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
    public String create(Principal principal, @Valid TodoCreationRequest todoCreationRequest, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "todo.create";
        }
        User user = userService.findByUsername(principal.getName());
        todoService.createTodo(todoCreationRequest.getTodo(), user);
        return "redirect:/";
    }

}
