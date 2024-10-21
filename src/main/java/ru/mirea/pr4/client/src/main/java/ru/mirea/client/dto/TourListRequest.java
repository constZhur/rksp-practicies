package ru.mirea.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.mirea.client.model.Tour;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TourListRequest {
    private List<Tour> tours;
}
