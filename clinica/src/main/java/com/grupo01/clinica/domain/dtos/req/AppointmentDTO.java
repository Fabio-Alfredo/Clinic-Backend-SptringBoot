package com.grupo01.clinica.domain.dtos.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AppointmentDTO {

    @NotBlank
    private String reason;
    private Date request;
}
