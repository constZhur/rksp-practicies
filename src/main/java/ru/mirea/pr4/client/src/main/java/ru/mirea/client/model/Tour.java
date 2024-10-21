package ru.mirea.client.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Tour {
    private Long id;
    private String title;
    private String description;
    private String country;
}
