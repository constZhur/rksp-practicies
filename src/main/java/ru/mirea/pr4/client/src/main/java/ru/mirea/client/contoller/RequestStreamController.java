package ru.mirea.client.contoller;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.client.model.Tour;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class RequestStreamController {

    private final RSocketRequester rSocketRequester;

    @GetMapping
    public Publisher<Tour> getTours() {
        return rSocketRequester
                .route("getTours")
                .data(new Tour())
                .retrieveFlux(Tour.class);
    }
}

