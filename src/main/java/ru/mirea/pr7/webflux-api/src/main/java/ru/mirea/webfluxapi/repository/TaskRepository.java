package ru.mirea.webfluxapi.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.mirea.webfluxapi.model.Task;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    Flux<Task> findByAuthor(String author);
}
