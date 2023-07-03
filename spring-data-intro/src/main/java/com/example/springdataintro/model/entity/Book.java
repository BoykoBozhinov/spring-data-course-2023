package com.example.springdataintro.model.entity;

import com.example.springdataintro.model.entity.enums.AgeRestriction;
import com.example.springdataintro.model.entity.enums.EditionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(name = "title", length = 60, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated()
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer copies;

    @Column(name = "release-date")
    private LocalDate releaseDate;

    @Enumerated()
    private AgeRestriction ageRestriction;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Set<Category> categories;

    public Book() {
    }

    public Book(EditionType editionType, LocalDate releaseDate, Integer copies, BigDecimal price,
                AgeRestriction ageRestriction, String title, Author author, Set<Category> categories) {
         this.editionType = editionType;
         this.releaseDate = releaseDate;
         this.copies = copies;
         this.price = price;
         this.ageRestriction = ageRestriction;
         this.title = title;
         this.author = author;
         this.categories = categories;
    }
}
