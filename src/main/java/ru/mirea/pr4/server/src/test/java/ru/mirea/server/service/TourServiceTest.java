package ru.mirea.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mirea.server.exception.TourNotFoundException;
import ru.mirea.server.model.Tour;
import ru.mirea.server.repository.TourRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TourServiceTest {
    @Mock
    private TourRepository tourRepository;
    @InjectMocks
    private TourService tourService;
    private Tour tour;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tour = new Tour(1L, "Test Tour", "Description", "Country");
    }
    @Test
    void getAllTours_ShouldReturnListOfTours() {
        List<Tour> tourList = new ArrayList<>();
        tourList.add(tour);

        when(tourRepository.findAll()).thenReturn(tourList);

        List<Tour> result = tourService.getAllTours();

        assertEquals(1, result.size());
        assertEquals(tour, result.get(0));
        verify(tourRepository, times(1)).findAll();
    }
    @Test
    void getTourById_ShouldReturnTour_WhenExists() {
        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));

        Tour result = tourService.getTourById(1L);

        assertEquals(tour, result);
        verify(tourRepository, times(1)).findById(1L);
    }
    @Test
    void getTourById_ShouldThrowException_WhenNotExists() {
        when(tourRepository.findById(1L)).thenReturn(Optional.empty());

        TourNotFoundException exception = assertThrows(TourNotFoundException.class, () -> {
            tourService.getTourById(1L);
        });

        assertEquals("Тур с идентификатором 1 не найден", exception.getMessage());
        verify(tourRepository, times(1)).findById(1L);
    }
    @Test
    void createTour_ShouldSaveTour() {
        when(tourRepository.save(any(Tour.class))).thenReturn(tour);

        Tour result = tourService.createTour(tour);

        assertEquals(tour, result);
        verify(tourRepository, times(1)).save(tour);
    }
    @Test
    void updateTour_ShouldUpdateAndReturnTour_WhenExists() {
        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));
        Tour updatedTour = new Tour(null, "Updated Tour", "Updated Description", "Updated Country");

        when(tourRepository.save(any(Tour.class))).thenAnswer(invocation -> {
            Tour savedTour = invocation.getArgument(0);
            savedTour.setId(1L);
            return savedTour;
        });

        Tour result = tourService.updateTour(1L, updatedTour);

        assertNotNull(result);
        assertEquals("Updated Tour", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Updated Country", result.getCountry());
        verify(tourRepository, times(1)).save(any(Tour.class));
    }
    @Test
    void updateTour_ShouldThrowException_WhenNotExists() {
        when(tourRepository.findById(1L)).thenReturn(Optional.empty());

        TourNotFoundException exception = assertThrows(TourNotFoundException.class, () -> {
            tourService.updateTour(1L, tour);
        });

        assertEquals("Тур с идентификатором 1 не найден", exception.getMessage());
        verify(tourRepository, times(1)).findById(1L);
    }
    @Test
    void deleteTour_ShouldDeleteTour_WhenExists() {
        when(tourRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> tourService.deleteTour(1L));
        verify(tourRepository, times(1)).deleteById(1L);
    }
    @Test
    void deleteTour_ShouldThrowException_WhenNotExists() {
        when(tourRepository.existsById(1L)).thenReturn(false);

        TourNotFoundException exception = assertThrows(TourNotFoundException.class, () -> {
            tourService.deleteTour(1L);
        });

        assertEquals("Тур с идентификатором 1 не найден", exception.getMessage());
        verify(tourRepository, times(1)).existsById(1L);
    }
}

