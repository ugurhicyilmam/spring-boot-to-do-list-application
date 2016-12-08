package com.ugurhicyilmam.service;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import com.ugurhicyilmam.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceImplTest {

    @MockBean
    private TodoRepository todoRepository;

    private TodoServiceImpl todoService;

    @Before
    public void setUp() throws Exception {
        todoService = new TodoServiceImpl(todoRepository);
    }

    @Test
    public void findTodos_shouldReturnWhatRepoReturns() throws Exception {
        Page<Todo> toBeReturned = new PageImpl<>(new ArrayList<>());
        when(todoRepository.findByUserOrderByCreatedAtDesc(any(PageRequest.class), any(User.class)))
                .thenReturn(toBeReturned);

        Page<Todo> returned = todoService.findTodos(0, 10, new User());

        Assert.assertEquals(toBeReturned, returned);
    }

    @Test
    public void createTodo_shouldSetUpTodoCorrectly() throws Exception {
        when(todoRepository.save(any(Todo.class))).then(returnsFirstArg());

        User creator = new User();

        Todo todo = todoService.createTodo("todo content", creator);

        Assert.assertEquals("todo content", todo.getContent());
        Assert.assertNotNull(todo.getCreatedAt());
        Assert.assertNull(todo.getUser());
    }

    @Test
    public void deleteTodo_shouldCallDeleteIfTodoFound() throws Exception {
        Todo todo = new Todo();

        when(todoRepository.findOne(anyLong()))
                .thenReturn(todo);

        todoService.deleteTodo(0L);

        verify(todoRepository, times(1)).delete(eq(todo));
    }

    @Test
    public void deleteTodo_shouldNotCallDeleteIfTodoNotFound() throws Exception {
        when(todoRepository.findOne(anyLong()))
                .thenReturn(null);

        todoService.deleteTodo(0L);

        verify(todoRepository, times(0)).delete(any(Todo.class));
    }
}