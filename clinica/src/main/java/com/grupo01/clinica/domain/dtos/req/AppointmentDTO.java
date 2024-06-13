package com.grupo01.clinica.domain.dtos.req;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AppointmentDTO {
    private String reason;
    private String email;
}
