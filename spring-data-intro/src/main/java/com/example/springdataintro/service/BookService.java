package com.example.springdataintro.service;

import com.example.springdataintro.model.entity.Book;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBookAfterYear(int year);

    List<String> findAllBooksAuthorsWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorsFullName(String firstName, String lastName);
}
