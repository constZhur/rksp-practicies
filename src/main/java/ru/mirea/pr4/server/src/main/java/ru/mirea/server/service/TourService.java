package ru.mirea.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.server.exception.TourNotFoundException;
import ru.mirea.server.model.Tour;
import ru.mirea.server.repository.TourRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;

    public List<Tour> getAllTours() { return tourRepository.findAll(); }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("Тур с идентификатором " + id + " не найден"));
    }

    public Tour createTour(Tour tour) { return tourRepository.save(tour); }

    public Tour updateTour(Long id, Tour updatedTour) {
        Tour tour = getTourById(id);
        tour.setTitle(updatedTour.getTitle());
        tour.setDescription(updatedTour.getDescription());
        tour.setCountry(updatedTour.getCountry());
        return tourRepository.save(tour);
    }

    public void deleteTour(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new TourNotFoundException("Тур с идентификатором " + id + " не найден");
        }
        tourRepository.deleteById(id);
    }
}

