package com.example.springdtoex.service;

import com.example.springdtoex.model.dto.UserLoginDto;
import com.example.springdtoex.model.dto.UserRegisterDto;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();
}
