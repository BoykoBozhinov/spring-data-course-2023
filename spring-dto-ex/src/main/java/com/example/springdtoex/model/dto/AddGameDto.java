package com.example.springdtoex.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AddGameDto {

    @Pattern(regexp = "[A-Z][a-z]{3,100}", message = ("Enter valid title!"))
    private String title;

    @Positive(message = "Enter valid price!")
    private BigDecimal price;

    @Positive(message = "Enter valid size!")
    private Double size;

    @Size(min = 11, max = 11, message = "Enter valid trailer!")
    private String trailer;

    @Pattern(regexp = "(https?).+", message = "Enter valid url!")
    private String thumbnailURL;

    @Size(min = 20, message = "Enter valid description!")
    private String description;

    private String releaseDate;

    public AddGameDto() {
    }

    public AddGameDto(String title, BigDecimal price, Double size, String trailer, String thumbnailURL, String description, String releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.releaseDate = releaseDate;
    }
}
