package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.dtos.res.GeneralResponse;
import com.grupo01.clinica.domain.entities.Role;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.service.contracts.RoleService;
import com.grupo01.clinica.service.contracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> getAllRoles(){
        try {
            User user = userService.findUserAuthenticated();
            List<Role> roles = user.getRoles();
            return GeneralResponse.getResponse(HttpStatus.OK, roles);
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

}
