package com.grupo01.clinica.domain.dtos.res;

import com.grupo01.clinica.domain.entities.Specialty;
import lombok.Data;

@Data
public class ScheduleAppointmentDTO {
    private Specialty speciality;
    private ScheduleDocDTO doctor;
}
