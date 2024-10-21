package ru.mirea.webfluxapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mirea.webfluxapi.model.Task;
import ru.mirea.webfluxapi.service.TaskService;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/author/{author}")
    public Flux<Task> getTasksByAuthor(@PathVariable String author) {
        return taskService.getTasksByAuthor(author);
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Mono<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @PutMapping("/{id}/complete")
    public Mono<Task> markTaskAsCompleted(@PathVariable Long id) {
        return taskService.markTaskAsCompleted(id);
    }

    @GetMapping("/buffer")
    public Flux<Task> getAllTasksWithBuffer() {
        return taskService.getAllTasksWithBuffer();
    }
}
