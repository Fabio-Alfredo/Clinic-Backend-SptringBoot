package com.grupo01.clinica.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.BinaryOperator;

@Entity
@Data
@Table(name="medical_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String reason;
    private Date realization;
    private Date finalization;
    private Date request;
    private String status;
    private boolean isAccepted;
    private Date schedulEndDate;

    @ColumnDefault(value = "false")
    @JsonIgnore
    private Boolean accepted;



    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Attends> attends;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

}
