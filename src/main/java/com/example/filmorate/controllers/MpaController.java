package com.example.filmorate.controllers;

import com.example.filmorate.model.Mpa;
import com.example.filmorate.service.MpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/mpa")
    public List<Mpa> findAll() {
        return mpaService.getAll();
    }

    @GetMapping("/mpa/{id}")
    public Mpa findById(@PathVariable Integer id) {
        return mpaService.getById(id);
    }

}