package ru.mirea.client.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.mirea.client.dto.UpdateTourRequest;
import ru.mirea.client.model.Tour;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class RequestResponseController {
    private final RSocketRequester rSocketRequester;

    @GetMapping("/{id}")
    public Mono<Tour> getCat(@PathVariable Long id) {
        return rSocketRequester.route("getTour")
                .data(id)
                .retrieveMono(Tour.class);
    }

    @PostMapping
    public Mono<Tour> addCat(@RequestBody Tour tour) {
        return rSocketRequester.route("addTour")
                .data(tour)
                .retrieveMono(Tour.class);
    }

    @PutMapping("/{id}")
    public Mono<Tour> updateCat(@PathVariable Long id, @RequestBody Tour updatedTour) {
        UpdateTourRequest updateRequest = new UpdateTourRequest(id, updatedTour);
        return rSocketRequester.route("updateTour")
                .data(updateRequest)
                .retrieveMono(Tour.class);
    }
}
