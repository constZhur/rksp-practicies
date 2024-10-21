package ru.mirea.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.server.model.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
