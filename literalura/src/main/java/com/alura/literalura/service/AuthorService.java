package com.alura.literalura.service;

import com.alura.literalura.model.Author;
import com.alura.literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    protected List<Author> getAllAuthors() { return authorRepository.findAll();}

    public void listAllAuthors(){
        List<Author> authors = getAllAuthors();
        for(Author author : authors){
            System.out.println(author);
        }
    }


    public void listAuthorsAlive(int date){
        List<Author> authors = authorRepository.findByDeathYearGreaterThanEqualOrDeathYearIsNull(date);
        for(Author author : authors){
            System.out.println(author);
        }
    }
}
