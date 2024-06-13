package com.grupo01.clinica.service.impl;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Attends;
import com.grupo01.clinica.domain.entities.Prescription;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.repositorie.AppointmentRepository;
import com.grupo01.clinica.repositorie.AttendsRepository;
import com.grupo01.clinica.repositorie.UserRepository;
import com.grupo01.clinica.service.contracts.AppointmentService;
import com.grupo01.clinica.service.contracts.AttendsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AttendsService attendsService;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository, AttendsService attendsService, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.attendsService = attendsService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createAppointment(AppointmentDTO req, User user){
        Appointment appointment = modelMapper.map(req, Appointment.class);
        appointment.setRequest(Date.from(Instant.now()));
        appointment.setUser(user);
        appointment.setStatus("PENDING");
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public boolean isDoctorAndAvailable(User doctor, Date startDate, Date endDate) {

        List<Attends> docSpecial = attendsService.findAllUserAndAppointmentDate(doctor, startDate, endDate);
        for (Attends a: docSpecial){
            List<Appointment>doctApp = appointmentRepository.findAllByAcceptedAndAttendsContaining(true, a).stream().filter(m->!m.getStatus().equals("FINISHED")).toList();
            for(Appointment appointment: doctApp) {
                if (appointment.getRealization().before(endDate) && appointment.getFinalization().after(startDate)) {
                    return false;
                } else if (startDate.before(appointment.getRealization()) && endDate.before(appointment.getFinalization())) {
                    return false;

                } else if (startDate.before(appointment.getFinalization()) && endDate.after(appointment.getFinalization())) {
                    return false;
                } else if (startDate.before(appointment.getRealization()) && endDate.after(appointment.getRealization())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Appointment findByIdAndDoc(UUID id, User user) {
        return appointmentRepository.findByIdAndUser(id, user).orElse(null);
    }

    @Override
    public Appointment findById(UUID id) {
        return appointmentRepository.findById(id).orElse(null );
    }

    @Override
    public boolean isDoctorAndAppointment(User doctor, Appointment appointment) {
        boolean isDoctor = false;
        for (Attends a: appointment.getAttends())
            if (a.getUser().getId().equals(doctor.getId())) {
                isDoctor = true;
                break;
            }
        return isDoctor;
    }


    @Override
    public void finishAppointment(Appointment appointment) {
        appointment.setStatus("FINISHED");
        appointment.setFinalization(Date.from(Instant.now()));
        appointmentRepository.save(appointment);
    }

    @Override
    public void savePrescriptions(List<Prescription> pres, Appointment appointment) {

        appointment.setPrescriptions(pres);
        appointmentRepository.save(appointment);
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }


    @Override
    public List<Appointment> getAppointments(UUID id, String status) {
        if(status != null && !status.isEmpty()){
            return appointmentRepository.findByIdAndStatus(id, status);
        }else {
            User user = userRepository.findById(id).orElse(null);
            if(user != null){
                return appointmentRepository.findByUser(user);
            }else {
                return null;
            }
        }
    }

//    @Override
//    public List<Appointment> findByD_realizationIn(Date realization) {
//        return appointmentRepository.findAppointmentsByRealization(realization);
//    }
//
//    @Override
//    public List<Appointment> findByUserAndRealization(User user, Date realization) {
//        return appointmentRepository.findAppointmentsByUserAndRealization(user, realization);
//    }
}
