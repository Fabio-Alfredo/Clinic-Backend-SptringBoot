package com.grupo01.clinica.service;

import com.grupo01.clinica.domain.dtos.req.UserRegisterDTO;
import com.grupo01.clinica.domain.entities.Token;
import com.grupo01.clinica.domain.entities.User;

public interface UserService {
    Token registerToken(User user) throws Exception;
    Boolean isTokenValid(User user, String token);
    void cleanTokens(User user) throws Exception;

    User findByemail(String email);
    Boolean isPasswordOk(User user, String password);
    User findUserAuthenticated();
    void createUser(UserRegisterDTO user);
}
