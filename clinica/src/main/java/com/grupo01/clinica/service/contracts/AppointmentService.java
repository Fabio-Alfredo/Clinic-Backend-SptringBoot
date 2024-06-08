package com.grupo01.clinica.service.contracts;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    void createAppointment(AppointmentDTO req, User user);
    List<Appointment>findAll();
    Appointment findById(UUID id);
    //List<Appointment>findAllByUser(User user);

}
