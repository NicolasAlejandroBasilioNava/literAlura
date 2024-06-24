package com.alura.literalura;

import com.alura.literalura.model.Book;
import com.alura.literalura.service.AuthorService;
import com.alura.literalura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class ConsoleApp implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Override
    public void run(String... args) throws Exception {
        Scanner input = new Scanner(System.in);
        int menuOption = 0;

        while(true){
            System.out.println("Elija la opción por su número:\n" +
                    "1. Buscar libro por título\n" +
                    "2. Listar libros registrados\n" +
                    "3. Listar autores registrados\n" +
                    "4. Listar autores vivos en un determinado año\n" +
                    "5. Listar libros por idioma\n" +
                    "0. Salir\n");

            menuOption = input.nextInt();

            if(menuOption == 0){
                System.out.println("Gracias por utilizar la aplicación");
                break;
            }

            switch (menuOption) {
                case 1:
                    System.out.println("Ingrese el título del libro:");
                    String title = input.next();
                    Book book = bookService.searchAndSaveBook(title);
                    System.out.println("Libro guardado: " + book);
                    break;
                case 2:
                    bookService.listAllBooks();
                    break;
                case 3:
                    authorService.listAllAuthors();
                    break;
                case 4:
                    System.out.println("Ingrese la fecha");
                    int date = input.nextInt();
                    authorService.listAuthorsAlive(date);
                    break;
                case 5:
                    Set<String> availableLanguages = bookService.getAvailableLanguages();
                    System.out.println("Idiomas disponibles:");
                    for (String lang : availableLanguages) {
                        System.out.println(lang);
                    }
                    System.out.println("Ingrese el idioma:");
                    String language = input.nextLine();
                    if (language.trim().isEmpty()) {
                        language = input.nextLine();
                    }
                    bookService.listBooksByLanguage(language);
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
