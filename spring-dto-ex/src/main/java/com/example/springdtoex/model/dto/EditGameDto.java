package com.example.springdtoex.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EditGameDto {

    private Long id;

    private BigDecimal price;

    private Double size;

    public EditGameDto() {
    }

    public EditGameDto(Long id, BigDecimal price, Double size) {
        this.id = id;
        this.price = price;
        this.size = size;
    }
}
