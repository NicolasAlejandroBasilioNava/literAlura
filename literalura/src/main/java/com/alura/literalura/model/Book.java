package com.alura.literalura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String language;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
    public String toString() {
        return String.format("ID: %d\nTítulo: %s\nIdioma: %s\nAutor: %s\n", id, title, language, author.getName());
    }
}
