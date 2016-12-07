package com.ugurhicyilmam.service;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Page<Todo> findTodos(int page, int limit, User user) {
        return todoRepository.findByUserOrderByCreatedAtDesc(new PageRequest(page, limit), user);
    }
}
