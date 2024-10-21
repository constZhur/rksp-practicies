package ru.mirea.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.server.model.Tour;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTourRequest {
    private Long id;
    private Tour updatedTour;
}