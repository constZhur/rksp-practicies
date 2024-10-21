package ru.mirea.webfluxapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mirea.webfluxapi.model.Task;
import ru.mirea.webfluxapi.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;
    private Task task;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task(1L, "Test Task", "This is a test task", "Author");
    }
    @Test
     void getTaskById_ShouldReturnTask_WhenExists() {
        when(taskRepository.findById(1L)).thenReturn(Mono.just(task));

        Mono<Task> result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Test Task", result.block().getName());
        verify(taskRepository).findById(1L);
    }
    @Test
    void getTaskById_ShouldReturnNotFound_WhenDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Task> result = taskService.getTaskById(1L);

        assertThrows(ResponseStatusException.class, result::block);
    }
    @Test
    void createTask_ShouldSaveTask_WhenValid() {
        when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(task));

        Mono<Task> result = taskService.createTask(task);

        assertNotNull(result);
        assertEquals("Test Task", result.block().getName());
        verify(taskRepository).save(task);
    }
    @Test
    void updateTask_ShouldUpdateTask_WhenExists() {
        when(taskRepository.findById(1L)).thenReturn(Mono.just(task));
        Task updatedTask = new Task(1L, "Updated Task", "Updated description", "Author");
        when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(updatedTask)); // Настройка метода save

        Mono<Task> result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.block().getName());
        verify(taskRepository).save(any(Task.class));
    }
    @Test
    void updateTask_ShouldReturnNotFound_WhenDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Mono.empty());
        Task updatedTask = new Task(1L, "Updated Task", "Updated description", "Author");

        Mono<Task> result = taskService.updateTask(1L, updatedTask);

        assertThrows(ResponseStatusException.class, result::block);
    }
    @Test
    void deleteTask_ShouldReturnNotFound_WhenDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = taskService.deleteTask(1L);

        assertThrows(ResponseStatusException.class, result::block);
    }
    @Test
    void markTaskAsCompleted_ShouldMarkTask_WhenExists() {
        when(taskRepository.findById(1L)).thenReturn(Mono.just(task));
        when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(task));

        Mono<Task> result = taskService.markTaskAsCompleted(1L);

        assertNotNull(result);
        assertTrue(result.block().getDescription().contains("(Выполнена!)"));
        verify(taskRepository).save(any(Task.class));
    }
    @Test
    void markTaskAsCompleted_ShouldReturnNotFound_WhenDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Task> result = taskService.markTaskAsCompleted(1L);

        assertThrows(ResponseStatusException.class, result::block);
    }
    @Test
    void getAllTasks_ShouldReturnFluxOfTasks() {
        when(taskRepository.findAll()).thenReturn(Flux.just(task));

        Flux<Task> result = taskService.getAllTasks();


        assertNotNull(result);
        assertEquals(1, result.count().block());
        verify(taskRepository).findAll();
    }
    @Test
    void getTasksByAuthor_ShouldReturnTasks_WhenAuthorExists() {
        when(taskRepository.findByAuthor("Author")).thenReturn(Flux.just(task));

        Flux<Task> result = taskService.getTasksByAuthor("Author");

        assertNotNull(result);
        assertEquals(1, result.count().block());
        verify(taskRepository).findByAuthor("Author");
    }
}
