package com.example.springdataintro.repository;

import com.example.springdataintro.model.entity.Author;
import com.example.springdataintro.model.entity.Book;
import com.example.springdataintro.model.entity.enums.AgeRestriction;
import com.example.springdataintro.model.entity.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> getAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal priceLessThan, BigDecimal priceGreaterThan);

    @Query("SELECT b FROM Book b WHERE YEAR(b.releaseDate) != :year")
    List<Book> findAllByNotReleasedInYear(Integer year);

    List<Book> findAllByTitleContainsIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE b.author.lastName LIKE :string")
    List<Book> findBookTitlesByAuthorsLastNameStartingWithString(String string);
    @Query("SELECT COUNT(b) AS count FROM Book b WHERE CHAR_LENGTH(b.title) > :length")
    int findBooksByTitleLengthLongerThanGivenNumber(Integer length);

    @Query("SELECT COUNT(b.copies) AS count FROM Book b WHERE b.author.firstName LIKE :firstName AND b.author.lastName LIKE :lastName")
    int findBookCopiesByAuthor(String firstName, String lastName);

    List<Book> findBookByTitle(String title);
}
