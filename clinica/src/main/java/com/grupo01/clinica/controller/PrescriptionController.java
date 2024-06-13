package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.dtos.req.PrescriptionDTO;
import com.grupo01.clinica.domain.dtos.res.GeneralResponse;
import com.grupo01.clinica.domain.entities.Appointment;
import com.grupo01.clinica.domain.entities.Prescription;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.service.contracts.AppointmentService;
import com.grupo01.clinica.service.contracts.PrescriptionService;
import com.grupo01.clinica.service.contracts.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    public PrescriptionController(PrescriptionService prescriptionService, AppointmentService appointmentService, UserService userService) {
        this.prescriptionService = prescriptionService;
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<GeneralResponse> savePrescription(@RequestBody PrescriptionDTO req){
       Appointment appointment = appointmentService.findById(req.getAppointment());
       if(appointment == null){
              return GeneralResponse.getResponse(HttpStatus.FOUND, "Appointment not found!");
       }
         prescriptionService.savePrescription(req, appointment);

        return GeneralResponse.getResponse(HttpStatus.OK, "Prescription saved!");
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> getAllPrescriptions(){
        try {
            return GeneralResponse.getResponse(HttpStatus.OK, prescriptionService.findAll());
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/clinic/prescriptions")
    public ResponseEntity<GeneralResponse> getPrescription(@RequestParam("id") UUID id){
        try {
            User user = userService.findBiId(id);
            User doc = userService.findUserAuthenticated();
            Date currentDate = new Date();

            if(user == null){
                return GeneralResponse.getResponse(HttpStatus.FOUND, "User not found!");
            }
            List<Appointment> appointments = user.getAppointments();
            List<Prescription>res = new ArrayList<>();
            for (Appointment appointment : appointments) {

                List<Prescription>prescriptions = appointment.getPrescriptions();
                for (Prescription prescription : prescriptions) {
                    if(currentDate.before(prescription.getD_finalization())){
                        res.add(prescription);
                    }
                }

            }
            return GeneralResponse.getResponse(HttpStatus.OK, res);
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!" + e.getMessage());
        }
    }
}
