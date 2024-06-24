package com.alura.literalura.service;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.dto.GutendexResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private static final String BASE_URL = "http://gutendex.com/books";
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public Book searchAndSaveBook(String title) {
        try {
            String url = BASE_URL + "?search=" + title;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String responseBody = response.body();

            System.out.println("HTTP Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            if (statusCode == 200 && responseBody != null && !responseBody.isEmpty()) {
                // Parse response and extract book data
                ObjectMapper mapper = new ObjectMapper();
                GutendexResponseDTO responseDTO = mapper.readValue(responseBody, GutendexResponseDTO.class);

                if (!responseDTO.getResults().isEmpty()) {
                    GutendexResponseDTO.BookDTO bookDTO = responseDTO.getResults().get(0);

                    // Check if the book already exists in the database
                    Optional<Book> existingBook = bookRepository.findByTitle(bookDTO.getTitle());
                    if (existingBook.isPresent()) {
                        System.out.println("\nEl libro ya está registrado.");
                        return existingBook.get();
                    }

                    Book book = new Book();
                    book.setTitle(bookDTO.getTitle());
                    book.setLanguage(bookDTO.getLanguages().get(0));

                    GutendexResponseDTO.AuthorDTO authorDTO = bookDTO.getAuthors().get(0);
                    Author author = authorRepository.findByName(authorDTO.getName())
                            .orElseGet(() -> {
                                Author newAuthor = new Author();
                                newAuthor.setName(authorDTO.getName());
                                newAuthor.setBirthYear(authorDTO.getBirthYear());
                                newAuthor.setDeathYear(authorDTO.getDeathYear());
                                return authorRepository.save(newAuthor);
                            });

                    book.setAuthor(author);

                    return bookRepository.save(book);
                } else {
                    System.out.println("\nNo se encontraron libros con el título proporcionado.");
                    return null;
                }
            } else {
                System.out.println("\nError en la respuesta de la API: " + statusCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void listAllBooks() {
        List<Book> books = getAllBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    public void listBooksByLanguage(String language) {
        List<Book> books = getBooksByLanguage(language);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public Set<String> getAvailableLanguages() {
        List<Book> books = getAllBooks();
        return books.stream()
                .map(Book::getLanguage)
                .collect(Collectors.toSet());
    }

}
