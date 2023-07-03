package com.example.springdataintro;

import com.example.springdataintro.model.entity.Book;
import com.example.springdataintro.service.AuthorService;
import com.example.springdataintro.service.BookService;
import com.example.springdataintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;

    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
        printAllBookTitlesAfterYear(2000);
        printAllAuthorsWithBookReleaseDateBeforeYear(1990);
        printAllAuthorsAndNumberOfTheirBooks();
        printAllBooksByAuthorName("George", "Powell");
    }

    private void printAllBooksByAuthorName(String firstName, String lastName) {
        bookService.findAllBooksByAuthorsFullName(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService.getAllAuthorsOrderedByTheirBooksCount()
                .forEach(System.out::println);
    }

    private void printAllAuthorsWithBookReleaseDateBeforeYear(int year) {
        bookService.findAllBooksAuthorsWithReleaseDateBeforeYear(year).
                forEach(System.out::println);
    }

    private void printAllBookTitlesAfterYear(int year) {
        bookService.getAllBookAfterYear(year)
                .stream().map(Book::getTitle).forEach(System.out::println);
    }
}
