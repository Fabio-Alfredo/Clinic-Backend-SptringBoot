package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.service.contracts.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/prescription")
public class PrescriptionController {


    private final AppointmentService appointmentService;

    public PrescriptionController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }



}
