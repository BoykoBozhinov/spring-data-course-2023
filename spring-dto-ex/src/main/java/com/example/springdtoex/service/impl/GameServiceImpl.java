package com.example.springdtoex.service.impl;

import com.example.springdtoex.model.dto.AddGameDto;
import com.example.springdtoex.model.dto.EditGameDto;
import com.example.springdtoex.model.entity.Game;
import com.example.springdtoex.repository.GameRepository;
import com.example.springdtoex.service.GameService;
import com.example.springdtoex.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(AddGameDto addGameDto) {
        Set<ConstraintViolation<AddGameDto>> violations = validationUtil.getViolations(addGameDto);

        if (!violations.isEmpty()) {
            violations.stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        Game game = modelMapper.map(addGameDto, Game.class);
        game.setReleaseDate(LocalDate.parse(addGameDto.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        gameRepository.save(game);
    }

    @Override
    public void editGame(Long id, BigDecimal price, Double size) {
        Game game = gameRepository.findById(id).orElse(null);

        if (game == null) {
            System.out.println("Invalid id!");
            return;
        }
        game.setPrice(price);
        game.setSize(size);
        gameRepository.save(game);
        System.out.printf("Edited %s%n", game.getTitle());
    }

    @Override
    public List<String> findAllGamesView() {
        return gameRepository.findAllGames().stream()
                .map(game -> String.format("%s, %.2f", game.getTitle(), game.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public String findGameDetailsView(String gameTitle) {
        Game game = gameRepository.findGameByTitle(gameTitle);
        return String.format("Title: %s%nPrice: %.2f%nDescription: %s%nReleaseDate: %s",
               game.getTitle(), game.getPrice(), game.getDescription(), game.getReleaseDate());
    }
}
