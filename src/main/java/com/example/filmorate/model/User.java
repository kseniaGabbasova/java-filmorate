package com.example.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private Integer id;
    @NotNull
    @Email(message = "Неверно введен почтовый адрес")
    private String email;
    @NonNull
    private String login;
    private String name;
    @Past(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
    @JsonIgnore
    private Set<Integer> friends;

    public void addFriend(Integer id) {
        this.friends.add(id);
    }
}
