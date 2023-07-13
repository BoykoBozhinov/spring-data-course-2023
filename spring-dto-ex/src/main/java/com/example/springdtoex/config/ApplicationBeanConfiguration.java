package com.example.springdtoex.config;

import com.example.springdtoex.model.dto.AddGameDto;
import com.example.springdtoex.model.entity.Game;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper =  new ModelMapper();

        modelMapper.typeMap(AddGameDto.class, Game.class)
                .addMappings(mapper -> mapper.map(AddGameDto::getThumbnailURL, Game::setImageThumbnail));

        return modelMapper;
    }
}
