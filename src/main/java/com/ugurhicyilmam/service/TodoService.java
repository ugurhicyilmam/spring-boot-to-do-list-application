package com.ugurhicyilmam.service;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import org.springframework.data.domain.Page;

public interface TodoService {
    Page<Todo> findTodos(int page, int limit, User user);
    Todo createTodo(String content, User user);
    void deleteTodo(long todoId);
}
