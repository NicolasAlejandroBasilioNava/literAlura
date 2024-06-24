package com.alura.literalura.model;


// jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int birthYear;
    private int deathYear;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @Override
    public String toString() {
        return String.format("%s (Nacimiento: %d, Muerte: %d)", name, birthYear, deathYear);
    }
}
