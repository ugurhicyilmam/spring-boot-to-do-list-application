package com.ugurhicyilmam.service;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public Todo createTodo(String content, User user) {
        Todo todo = new Todo();
        todo.setContent(content);
        todo.setUser(user);
        todo.setCreatedAt(new Date());
        todoRepository.save(todo);
        return todo;
    }

    @Override
    public void deleteTodo(long todoId) {
        Todo todo = todoRepository.findOne(todoId);
        if(todo != null)
            todoRepository.delete(todo);

    }
}
