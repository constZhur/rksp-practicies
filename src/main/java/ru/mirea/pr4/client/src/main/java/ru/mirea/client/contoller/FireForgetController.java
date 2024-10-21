package ru.mirea.client.contoller;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class FireForgetController {

    private final RSocketRequester rSocketRequester;

    @DeleteMapping("/{id}")
    public Publisher<Void> deleteCat(@PathVariable Long id) {
        return rSocketRequester
                .route("deleteTour")
                .data(id)
                .send();
    }
}
