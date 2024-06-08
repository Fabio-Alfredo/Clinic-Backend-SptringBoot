package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.dtos.req.ApprovedAppointmentDTO;
import com.grupo01.clinica.domain.dtos.res.GeneralResponse;
import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Role;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.service.contracts.AppointmentService;
import com.grupo01.clinica.service.contracts.RoleService;
import com.grupo01.clinica.service.contracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final RoleService roleService;

    public AppointmentController(UserService userService, AppointmentService appointmentService, RoleService roleService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse>createAppointment(@RequestBody AppointmentDTO req) {
        try {

            User user = userService.findByemail(req.getEmail());
            if (user == null) {
                return GeneralResponse.getResponse(HttpStatus.FOUND, "User not found!");
            }
            appointmentService.createAppointment(req, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Appointment created!");

        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMN', 'ASST')")
    public ResponseEntity<GeneralResponse>getAllAppointments(){
        try {
            List<Appointment> res = appointmentService.findAll();
            if(res.isEmpty()){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, List.of("No appointments found!"));
            }
            return GeneralResponse.getResponse(HttpStatus.OK, res);
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/accept")
    public ResponseEntity<GeneralResponse>acceptAppointment(@RequestBody ApprovedAppointmentDTO req){
        try {
            return null;
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!"+e.getMessage());
        }
    }
}
