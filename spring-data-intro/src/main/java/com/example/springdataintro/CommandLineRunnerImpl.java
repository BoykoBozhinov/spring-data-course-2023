package com.example.springdataintro;

import com.example.springdataintro.model.entity.Book;
import com.example.springdataintro.model.entity.enums.AgeRestriction;
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
        //printAllBookTitlesAfterYear(2000);
        //printAllAuthorsWithBookReleaseDateBeforeYear(1990);
        //printAllAuthorsAndNumberOfTheirBooks();
        //printAllBooksByAuthorName("George", "Powell");
        //printAllBookTitlesByAgeRestriction("teEN".toUpperCase());
        //printAllGoldenBooksTitlesWithLessThanCopiesCount("GOLD", 5000);
        //printAllBooksTitlesAndPriceByPriceLessThanAndGreaterThan(5, 40);
        //printAllBooksTitlesNotReleasedInYear(2000);
        //printTitleEditionTypePriceOfBookBeforeYear(12, 4, 1992);
        //printAuthorsWithFirstNameEndsWithLetter("dy");
        //printBooksTitlesContainingGivenString("sK");
        //printBooksByAuthorsLastNameStartsWith("Ric");
        //printBooksCountWithTitleLongerThanGivenNumber(12);
        //printBookCopiesByAuthor("Randy", "Graham");
        printBookByTitle("Things Fall Apart");
    }

    private void printBookByTitle(String title) {
        bookService.findBookByTitle(title)
                .forEach(System.out::println);
    }

    private void printBookCopiesByAuthor(String firstName, String lastName) {
        int booksByAuthor = bookService.findBooksByAuthor(firstName, lastName);
        System.out.printf("%s %s - %d", firstName, lastName, booksByAuthor);
    }

    private void printBooksCountWithTitleLongerThanGivenNumber(Integer length) {
        int booksCount = bookService.findBooksCountWithTitleLongerThanGivenNumber(length);
        System.out.printf("There are %d books with longer titles than %d symbols.", booksCount, length);
    }

    private void printBooksByAuthorsLastNameStartsWith(String startsWith) {
        bookService.findBooksByAuthorsLastNameStartsWithString(startsWith + "%")
                .forEach(System.out::println);
    }

    private void printBooksTitlesContainingGivenString(String title) {
        bookService.findBookTitlesContainingGivenString(title)
                .forEach(System.out::println);
    }

    private void printAuthorsWithFirstNameEndsWithLetter(String letter) {
        authorService.getAllAuthorsFirstNameStartsWithGivenLetter(letter)
                .forEach(System.out::println);
    }

    private void printTitleEditionTypePriceOfBookBeforeYear(Integer day, Integer month, Integer year) {
        bookService.findBookTitleEditionTypeAndPriceReleasedBeforeDate(year, month, day)
                .forEach(System.out::println);
    }

    private void printAllBooksTitlesNotReleasedInYear(Integer year) {
        bookService.findAllBooksTitlesNotReleasedInYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksTitlesAndPriceByPriceLessThanAndGreaterThan(Integer priceLessThan, Integer priceGreaterThan) {
        bookService.findAllBooksPricesAndTitlesByPriceLessThanAndGreaterThan(priceLessThan, priceGreaterThan)
                .forEach(System.out::println);
    }

    private void printAllGoldenBooksTitlesWithLessThanCopiesCount(String editionType, Integer copies) {
        bookService.findAllGoldenEditionBookTitlesWithLessThanCopiesCount(editionType, copies)
                .forEach(System.out::println);
    }

    private void printAllBookTitlesByAgeRestriction(String ageRestriction) {
        bookService.findAllBookTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
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
