package com.grupo01.clinica.domain.dtos.res;

import com.grupo01.clinica.domain.entities.Attends;
import com.grupo01.clinica.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendsResponseDTO {
    private User principalDoc;
    private AppointmentDoctorDTO appointments;

}
