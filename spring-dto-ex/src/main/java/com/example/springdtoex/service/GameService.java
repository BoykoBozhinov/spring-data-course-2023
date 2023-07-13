package com.example.springdtoex.service;

import com.example.springdtoex.model.dto.AddGameDto;
import com.example.springdtoex.model.entity.Game;

import java.math.BigDecimal;
import java.util.List;

public interface GameService {

    void addGame(AddGameDto addGameDto);

    void editGame(Long id, BigDecimal price, Double size);

    List<String> findAllGamesView();

    String findGameDetailsView(String game);
}
