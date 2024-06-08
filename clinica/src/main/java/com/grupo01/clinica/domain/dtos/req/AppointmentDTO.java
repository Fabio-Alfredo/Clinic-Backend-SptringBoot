package com.grupo01.clinica.domain.dtos.req;

import lombok.Data;

@Data
public class AppointmentDTO {
    private String reason;
    private String email;
}
