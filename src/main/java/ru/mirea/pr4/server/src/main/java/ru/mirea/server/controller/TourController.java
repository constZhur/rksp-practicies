package ru.mirea.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mirea.server.dto.UpdateTourRequest;
import ru.mirea.server.model.Tour;
import ru.mirea.server.service.TourService;

@Controller
@RequiredArgsConstructor
public class TourController {
    private final TourService tourService;

    @MessageMapping("getTour")
    public Mono<Tour> getTour(Long id) {
        return Mono.justOrEmpty(tourService.getTourById(id));
    }

    @MessageMapping("getTours")
    public Flux<Tour> getTours() {
        return Flux.fromIterable(tourService.getAllTours());
    }

    @MessageMapping("addTour")
    public Mono<Void> addTour(Tour tour) {
        return Mono.justOrEmpty(tourService.createTour(tour)).then();
    }

    @MessageMapping("updateTour")
    public Mono<Tour> updateTour(UpdateTourRequest updateRequest) {
        Long id = updateRequest.getId();
        Tour updatedTour = updateRequest.getUpdatedTour();

        return Mono.fromCallable(() -> tourService.updateTour(id, updatedTour));
    }

    @MessageMapping("deleteTour")
    public Mono<Void> deleteTour(Long id) {
        return Mono.fromRunnable(() -> tourService.deleteTour(id));
    }

    @MessageMapping("tourChannel")
    public Flux<Tour> tourChannel(Flux<Tour> tours) {
        return tours.flatMap(tour -> Mono.fromCallable(() -> tourService.createTour(tour)))
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }
}
