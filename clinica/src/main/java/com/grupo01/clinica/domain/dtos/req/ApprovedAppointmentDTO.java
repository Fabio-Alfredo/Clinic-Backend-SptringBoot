package com.grupo01.clinica.domain.dtos.req;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ApprovedAppointmentDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date realization;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date schedulEndDate;
    private Boolean isAccepted;
    private UUID userId;
    private int duration;
    private UUID appointmentId;
    private List<String> specialists;
    private List<String> doctorEmail;

}
