package com.example.springdataintro.service.Implementation;

import com.example.springdataintro.model.entity.*;
import com.example.springdataintro.model.entity.enums.AgeRestriction;
import com.example.springdataintro.model.entity.enums.EditionType;
import com.example.springdataintro.repository.BookRepository;
import com.example.springdataintro.service.AuthorService;
import com.example.springdataintro.service.BookService;
import com.example.springdataintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {

        if (bookRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(BOOKS_FILE_PATH)).
                forEach(row -> {
                    String[] bookInfo = row.split("\\s+");
                    Book book = createBookFromInfo(bookInfo);
                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> getAllBookAfterYear(int year) {
        return bookRepository.getAllByReleaseDateAfter(LocalDate.of(year,12, 31));
    }

    @Override
    public List<String> findAllBooksAuthorsWithReleaseDateBeforeYear(int year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(year, 1, 1)).
                stream().map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName())).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorsFullName(String firstName, String lastName) {
        return bookRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream().map(book -> String.format("%s %s %d", book.getTitle(), book.getReleaseDate(), book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookTitlesByAgeRestriction(String ageRestriction) {
        return bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction))
                .stream().map(book -> String.format("%s", book.getTitle())).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGoldenEditionBookTitlesWithLessThanCopiesCount(String editionType, Integer copies) {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.valueOf(editionType), copies)
                .stream().map(book -> String.format("%s", book.getTitle())).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksPricesAndTitlesByPriceLessThanAndGreaterThan(Integer priceLessThan, Integer priceGreaterThan) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(priceLessThan),
                BigDecimal.valueOf(priceGreaterThan)).stream().map(book -> String.format("%s - $%.2f", book.getTitle(), book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksTitlesNotReleasedInYear(Integer year) {
        return bookRepository.findAllByNotReleasedInYear(year)
                .stream().map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBookTitleEditionTypeAndPriceReleasedBeforeDate(Integer day, Integer month, Integer year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(day, month, year))
                .stream().map(book -> String.format("%s %s %.2f", book.getTitle(), book.getEditionType(), book.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBookTitlesContainingGivenString(String title) {
        return bookRepository.findAllByTitleContainsIgnoreCase(title)
                .stream().map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findBooksByAuthorsLastNameStartsWithString(String startsWith) {
        return bookRepository.findBookTitlesByAuthorsLastNameStartingWithString(startsWith)
                .stream().map(book -> String.format("%s", book.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public int findBooksCountWithTitleLongerThanGivenNumber(Integer length) {
        return bookRepository.findBooksByTitleLengthLongerThanGivenNumber(length);
    }

    @Override
    public int findBooksByAuthor(String firstName, String lastName) {
        return bookRepository.findBookCopiesByAuthor(firstName, lastName);
    }

    @Override
    public List<String> findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .stream().map(book -> String.format("%s %s %s %.2f",
                        book.getTitle(),
                        book.getEditionType(),
                        book.getAgeRestriction(),
                        book.getPrice()))
                .collect(Collectors.toList());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate.parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo).skip(5).collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService.getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);
    }
}
