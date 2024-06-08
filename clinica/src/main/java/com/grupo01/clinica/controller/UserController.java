package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.dtos.res.GeneralResponse;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.service.contracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMN')")
    public ResponseEntity<GeneralResponse> getAllUsers(){
        try {
            List<User> users = userService.getAllUsers();
            return GeneralResponse.getResponse(HttpStatus.OK, users);
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMN')")
    public ResponseEntity<GeneralResponse> getUser(@RequestParam("id")UUID id){
        try {
            User user = userService.findBiId(id);
            return GeneralResponse.getResponse(HttpStatus.OK, user);
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }



}
