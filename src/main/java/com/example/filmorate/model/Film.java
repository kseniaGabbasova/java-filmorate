package com.example.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Film {
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    private LocalDate releaseDate;
    @Size(max = 200)
    private String description;
    @Positive
    private int duration;
    private List<Genre> genres;
    private Mpa mpa;
}
