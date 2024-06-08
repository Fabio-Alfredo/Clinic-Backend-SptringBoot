package com.grupo01.clinica.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String medicine;
    private String dose;
    private String frequency;
    private String duration;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Appointment appointment;

}
