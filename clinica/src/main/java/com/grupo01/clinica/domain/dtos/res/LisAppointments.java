package com.grupo01.clinica.domain.dtos.res;

import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Historic;
import com.grupo01.clinica.domain.entities.User;
import lombok.Data;

import java.util.List;

@Data
public class LisAppointments {
    private  Appointment appointments;
    private  User  patient;
    private List<User> doctors;
    private List<Historic> historics;
}
