package com.example.filmorate.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Builder
public class Mpa {
    private final int id;
    private final String name;
}