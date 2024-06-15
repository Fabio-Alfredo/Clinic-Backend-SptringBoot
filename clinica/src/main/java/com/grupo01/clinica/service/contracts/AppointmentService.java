package com.grupo01.clinica.service.contracts;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Prescription;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.repositorie.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    void createAppointment(AppointmentDTO req, User user);
    List<Appointment>findAll();
    boolean isDoctorAndAvailable(User doctor,Date startDate, Date endDate);
  
    Appointment findByIdAndDoc(UUID id, User user);
    Appointment findById(UUID id);
    boolean isDoctorAndAppointment(User doctor, Appointment appointment);
    List<Appointment> findAllByStatus(String status);
    //void updateStatus(List<User> doctors, Appointment appointment, String status);

    List<Appointment> getAppointments(User user, String status);
    void finishAppointment(Appointment appointment);
    void savePrescriptions(List<Prescription> pres, Appointment appointment);
    void saveAppointment(Appointment appointment);
//    List<Appointment> findByD_realizationIn(Date realization);
//    List<Appointment>findByUserAndRealization(User user, Date realization);
    //List<Appointment>findAllByUser(User user);


}
