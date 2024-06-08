package com.grupo01.clinica.repositorie;

import com.grupo01.clinica.domain.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID>{

        List<Appointment> findByPatientIdandStatus(UUID id, String status);

        List<Appointment> findByPatientId(UUID id);
}
