package ru.mirea.taskserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.taskserver.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
