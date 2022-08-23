package com.example.filmorate.model;

import lombok.*;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Builder
public class Genre {
    private final int id;
    private final String name;
}
