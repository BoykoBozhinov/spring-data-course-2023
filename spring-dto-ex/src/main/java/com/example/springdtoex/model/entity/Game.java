package com.example.springdtoex.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @Column
    private String title;

    @Column
    private String trailer;

    @Column(name = "image_thumbnail")
    private String imageThumbnail;

    @Column
    private Double size;

    @Column
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {
    }
}
