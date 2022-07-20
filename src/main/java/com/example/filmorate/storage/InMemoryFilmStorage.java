package com.example.filmorate.storage;

import com.example.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private HashMap<Integer, Film> films = new LinkedHashMap<>();

    public Film create(Film film) {
        if (save(film)) {
            film.setWhoLiked(new HashSet<>());
            log.info("Добавлен фильм: {}", film);
            return film;
        } else {
            return null;
        }
    }

    public Film update(Film film) {
        film.setWhoLiked(films.get(film.getId()).getWhoLiked());
        films.replace(film.getId(), film);
        log.info("Фильм обновлен: {}", film);
        return film;
    }

    public Collection<Film> getFilms() {
        return films.values();
    }

    public Film getFilmById(int id) {
        return films.getOrDefault(id, null);
    }

    public void putLike(Film film, int userId) {
        film.getWhoLiked().add(userId);
        log.info("Добавлен лайк к фильму: {}", film);
    }

    public void deleteLike(Film film, int userId) {
        film.getWhoLiked().remove(userId);
        log.info("Удален лайк к фильму: {}", film);
    }


    public List<Film> getByPopularity(int count) {
        Collection<Film> filmsToSort = films.values();
        List<Film> filmsToReturn = new ArrayList<>(filmsToSort);
        filmsToReturn.sort((o1, o2) -> o2.getWhoLiked().size() - o1.getWhoLiked().size());
        if (filmsToReturn.size() < count) {
            return filmsToReturn;
        } else {
            return filmsToReturn.subList(0, count);
        }
    }

    private boolean save(Film film) {
        int max = 0;
        if (films.isEmpty()) {
            film.setId(1);
        }
        for (int i : films.keySet()) {
            if (i > max) {
                max = i;
            }
        }
        film.setId(max + 1);
        films.put(max + 1, film);
        return true;
    }
}
