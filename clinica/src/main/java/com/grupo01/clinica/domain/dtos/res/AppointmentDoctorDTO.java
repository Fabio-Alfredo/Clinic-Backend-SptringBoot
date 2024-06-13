package com.grupo01.clinica.domain.dtos.res;

import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Attends;
import com.grupo01.clinica.domain.entities.Historic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDoctorDTO {
    private List<Historic> historics;
    private List<ScheduleAppointmentDTO> doctors;
    private Appointment appointment;
}
