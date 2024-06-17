package com.grupo01.clinica.domain.dtos.res;

import lombok.Data;

import java.util.UUID;

@Data
public class AppointmentResponseDTO {
    public UUID id;
    public String name;
    public String reason;
}
