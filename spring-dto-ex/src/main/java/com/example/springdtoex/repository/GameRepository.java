package com.example.springdtoex.repository;

import com.example.springdtoex.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g")
    List<Game> findAllGames();

    Game findGameByTitle(String title);
}
