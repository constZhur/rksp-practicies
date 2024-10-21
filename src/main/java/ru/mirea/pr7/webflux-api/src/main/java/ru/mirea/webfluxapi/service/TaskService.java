package ru.mirea.webfluxapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mirea.webfluxapi.model.Task;
import ru.mirea.webfluxapi.repository.TaskRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    public Mono<Task> getTaskById(Long id) {
        log.info("Запрашивается задача с идентификатором: {}", id);
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"))
                )
                .onErrorResume(e -> {
                    log.error("Ошибка при получении задачи с идентификатором: {}, ошибка: {}", id, e.getMessage());
                    return Mono.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении задачи")
                    );
                });
    }

    public Flux<Task> getAllTasks() {
        log.info("Запрашиваются все задачи");
        return taskRepository.findAll()
                .onErrorResume(e -> {
                    log.error("Ошибка при получении всех задач: {}", e.getMessage());
                    return Flux.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении списка задач")
                    );
                });
    }

    public Mono<Task> createTask(Task task) {
        log.info("Создание новой задачи: {}", task);
        return taskRepository.save(task)
                .onErrorResume(e -> {
                    log.error("Не удалось создать задачу: {}, ошибка: {}", task, e.getMessage());
                    return Mono.error(
                            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка при создании задачи")
                    );
                });
    }

    public Mono<Task> updateTask(Long id, Task updatedTask) {
        log.info("Обновление задачи с идентификатором: {}", id);
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"))
                )
                .flatMap(existingTask -> {
                    existingTask.setName(updatedTask.getName());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setAuthor(updatedTask.getAuthor());
                    log.info("Данные задачи обновлены: {}", existingTask);
                    return taskRepository.save(existingTask);
                })
                .onErrorResume(e -> {
                    log.error("Ошибка при обновлении задачи с идентификатором: {}, ошибка: {}", id, e.getMessage());
                    return Mono.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при обновлении задачи")
                    );
                });
    }

    public Mono<Void> deleteTask(Long id) {
        log.info("Удаление задачи с идентификатором: {}", id);
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"))
                )
                .flatMap(taskRepository::delete)
                .onErrorResume(e -> {
                    log.error("Не удалось удалить задачу с идентификатором: {}, ошибка: {}", id, e.getMessage());
                    return Mono.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при удалении задачи")
                    );
                });
    }

    public Flux<Task> getTasksByAuthor(String author) {
        log.info("Запрашиваются задачи автора: {}", author);
        return taskRepository.findByAuthor(author)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Задачи для этого автора не найдены"))
                )
                .onErrorResume(e -> {
                    log.error("Ошибка при получении задач для автора: {}, ошибка: {}", author, e.getMessage());
                    return Flux.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении задач автора")
                    );
                });
    }

    public Mono<Task> markTaskAsCompleted(Long id) {
        log.info("Помечаем задачу с id: {} как выполненную", id);
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача с данным id не найдена"))
                )
                .flatMap(task -> {
                    task.setDescription(task.getDescription() + "\n(Выполнена!)");
                    return taskRepository.save(task);
                })
                .onErrorResume(e -> {
                    log.error("Ошибка при пометке задачи с id: {} как выполненной, ошибка: {}", id, e.getMessage());
                    return Mono.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при обновлении задачи")
                    );
                });
    }

    public Flux<Task> getAllTasksWithBuffer() {
        log.info("Запрашиваются все задачи с буферизацией");
        return taskRepository.findAll()
                .onBackpressureBuffer(3)
                .onErrorResume(e -> {
                    log.error("Ошибка при получении всех задач: {}", e.getMessage());
                    return Flux.error(
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при получении списка задач")
                    );
                });
    }
}
