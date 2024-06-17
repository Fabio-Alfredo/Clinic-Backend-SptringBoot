package com.grupo01.clinica.service.impl;

import com.grupo01.clinica.domain.entities.Attends;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.repositorie.AttendsRepository;
import com.grupo01.clinica.service.contracts.AttendsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
public class AttendsServiceImpl implements AttendsService {

    private final AttendsRepository attendsRepository;

    public AttendsServiceImpl(AttendsRepository attendsRepository) {
        this.attendsRepository = attendsRepository;
    }

    @Override
    public List<Attends> fidAllUserAndAppointmentRealization(User user, Date realization) {
        return attendsRepository.findAllByUserAndAppointmentRealization(user, realization);
    }

    @Override
    public List<Attends> findAllUserAndAppointmentDate(User user, Date start, Date end) {
        return attendsRepository.findAllByUserAndAppointmentRealizationBetween(user, start, end);
    }

    @Override
    public List<Attends> findAllUser(User doc) {
        return attendsRepository.findAllByUser(doc);
    }

    @Override
    public void saveAttend(Attends attend) {
        attendsRepository.save(attend);
    }
}
