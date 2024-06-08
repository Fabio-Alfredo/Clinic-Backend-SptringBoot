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
    private Date d_finalization;
    private String medicine;
    private String dosage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Appointment appointment;

}
