package ru.mirea.client.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.mirea.client.dto.TourListRequest;
import ru.mirea.client.model.Tour;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class ChannelController {

    private final RSocketRequester rSocketRequester;

    @PostMapping("/exp")
    public Flux<Tour> addToursMultiple(@RequestBody TourListRequest
                                             tourListRequest){
        List<Tour> tourList = tourListRequest.getTours();
        Flux<Tour> tours = Flux.fromIterable(tourList);
        return rSocketRequester
                .route("tourChannel")
                .data(tours)
                .retrieveFlux(Tour.class);
    }
}
