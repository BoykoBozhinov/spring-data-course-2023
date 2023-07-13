package com.example.springdataintro.service;

import com.example.springdataintro.model.entity.Book;
import com.example.springdataintro.model.entity.enums.AgeRestriction;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBookAfterYear(int year);

    List<String> findAllBooksAuthorsWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorsFullName(String firstName, String lastName);

    List<String> findAllBookTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllGoldenEditionBookTitlesWithLessThanCopiesCount(String editionType, Integer copies);

    List<String> findAllBooksPricesAndTitlesByPriceLessThanAndGreaterThan(Integer priceLessThan, Integer priceGreaterThan);

    List<String> findAllBooksTitlesNotReleasedInYear(Integer year);

    List<String> findBookTitleEditionTypeAndPriceReleasedBeforeDate(Integer day, Integer month, Integer year);

    List<String> findBookTitlesContainingGivenString(String title);

    List<String> findBooksByAuthorsLastNameStartsWithString(String startsWith);

    int findBooksCountWithTitleLongerThanGivenNumber(Integer length);

    int findBooksByAuthor(String firstName, String lastName);

    List<String> findBookByTitle(String title);
}
