package com.example.springdtoex.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column
    @ManyToMany
    private Set<Game> games;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    public User() {
    }
}
