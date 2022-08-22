package com.example.filmorate.controllers;

import com.example.filmorate.model.Genre;
import com.example.filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> findAll() {
        return genreService.getAll();
    }

    @GetMapping("/genres/{id}")
    public Genre findById(@PathVariable Integer id) {
        return genreService.getById(id);
    }

}
