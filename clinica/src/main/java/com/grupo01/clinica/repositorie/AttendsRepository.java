package com.grupo01.clinica.repositorie;

import com.grupo01.clinica.domain.entities.Attends;
import com.grupo01.clinica.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendsRepository extends JpaRepository<Attends, UUID> {
    List<Attends> findAllByUserAndAppointmentRealization(User user, Date realization);
    List<Attends>findAllByUserAndAppointmentRealizationBetween(User user, Date star, Date end);
}
