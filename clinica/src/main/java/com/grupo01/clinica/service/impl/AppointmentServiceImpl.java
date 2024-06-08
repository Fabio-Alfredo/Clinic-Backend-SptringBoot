package com.grupo01.clinica.service.impl;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Prescription;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.repositorie.AppointmentRepository;
import com.grupo01.clinica.service.contracts.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createAppointment(AppointmentDTO req, User user){
        Appointment appointment = modelMapper.map(req, Appointment.class);
        appointment.setD_realization(Date.from(Instant.now()));
        appointment.setUser(user);
        appointment.setStatus("PENDING");
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment findById(UUID id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public void finishAppointment(Appointment appointment) {
        appointment.setStatus("FINISHED");
        appointment.setD_finalization(Date.from(Instant.now()));
        appointmentRepository.save(appointment);
    }

    @Override
    public void savePrescriptions(List<Prescription> pres, Appointment appointment) {
        appointment.setPrescriptions(pres);
        appointmentRepository.save(appointment);
    }

}
