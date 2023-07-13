package com.example.springdtoex.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    private User buyer;

    @ManyToMany
    private Set<Game> games;

    public Order() {
    }
}
