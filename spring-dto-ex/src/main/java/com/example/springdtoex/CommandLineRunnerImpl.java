package com.example.springdtoex;

import com.example.springdtoex.model.dto.AddGameDto;
import com.example.springdtoex.model.dto.UserLoginDto;
import com.example.springdtoex.model.dto.UserRegisterDto;
import com.example.springdtoex.service.GameService;
import com.example.springdtoex.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]) {
                case "RegisterUser" -> userService
                        .registerUser(new UserRegisterDto(commands[1], commands[2], commands[3], commands[4]));
                case "LoginUser" -> userService
                        .loginUser(new UserLoginDto(commands[1], commands[2]));
                case "Logout" -> userService.logout();

                case "AddGame" -> gameService.addGame(new AddGameDto(commands[1], new BigDecimal(commands[2]),
                        Double.parseDouble(commands[3]), commands[4], commands[5], commands[6], commands[7]));

                case "EditGame" -> {
                    String[] priceValues = commands[2].split("=");
                    String[] sizeValues = commands[3].split("=");
                    gameService.editGame(Long.parseLong(commands[1]),
                    new BigDecimal(priceValues[1]), Double.parseDouble(sizeValues[1]));
                }

                case "AllGames" -> gameService.findAllGamesView().forEach(System.out::println);

                case "DetailGame" -> System.out.println(gameService.findGameDetailsView(commands[1]));
            }
        }
    }
}
