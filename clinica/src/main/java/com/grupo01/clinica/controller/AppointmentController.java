package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.dtos.req.ApprovedAppointmentDTO;
import com.grupo01.clinica.domain.dtos.req.PrescriptionCreateDTO;
import com.grupo01.clinica.domain.dtos.res.GeneralResponse;
import com.grupo01.clinica.domain.dtos.res.LisAppointments;
import com.grupo01.clinica.domain.entities.*;
import com.grupo01.clinica.service.contracts.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final RoleService roleService;
    private final PrescriptionService prescriptionService;
    private final AttendsService attendsService;

    public AppointmentController(UserService userService, AppointmentService appointmentService, RoleService roleService, PrescriptionService prescriptionService, AttendsService attendsService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.roleService = roleService;
        this.prescriptionService = prescriptionService;
        this.attendsService = attendsService;
    }

    @PostMapping("/request")
    public ResponseEntity<GeneralResponse>createAppointment(@RequestBody AppointmentDTO req) {
        try {

            User user = userService.findByemail(req.getEmail());
            if (user == null) {
                return GeneralResponse.getResponse(HttpStatus.FOUND, "User not found!");
            }
            appointmentService.createAppointment(req, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Appointment created!");

        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMN', 'ASST')")
    public ResponseEntity<GeneralResponse>getAllAppointments(){
        try {
            List<Appointment> res = appointmentService.findAll();
            if(res.isEmpty()){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, List.of("No appointments found!"));
            }
            return GeneralResponse.getResponse(HttpStatus.OK, res);
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @PostMapping("/finish")
    @PreAuthorize("hasAnyAuthority('DCTR')")
    public ResponseEntity<GeneralResponse>finishAppointment(@RequestParam("id") UUID id){
        try {
            Appointment appointment = appointmentService.findById(id);
            if(appointment == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Appointment not found!");
            }
            appointmentService.finishAppointment(appointment);
            return GeneralResponse.getResponse(HttpStatus.OK, "Appointment finished!");
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @PostMapping("/clinic/prescription")
//    @PreAuthorize("hasAnyAuthority( 'DCTR')")
    public ResponseEntity<GeneralResponse>prescriptionAppointment(@RequestBody PrescriptionCreateDTO req){
        try {
            Appointment appointment = appointmentService.findById(req.getAppointment());
            if(appointment == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Appointment not found!");
            }
            List<Prescription>pres= prescriptionService.savePrescriptionList(req.getPrescriptions(), appointment);
            if(pres.isEmpty()){
                return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
            }
            appointmentService.savePrescriptions(pres, appointment);
            return GeneralResponse.getResponse(HttpStatus.OK, "Prescription added!");
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }

    }

    @GetMapping("/own")
    @PreAuthorize("hasAnyAuthority('PCTE')")
        public List<Appointment> getPatientAppointments( @RequestParam (required = false) String status){
        User user = userService.findUserAuthenticated();
        return appointmentService.getAppointments(user.getId(), status);
    }

//    @GetMapping("/clinic/schedule")
//    public List<Appointment> getDoctorAppointments(@RequestParam("id") Date fecha){
//        List<Appointment> appointments = appointmentService.findByD_realizationIn(fecha);
//        List<LisAppointments> res = new ArrayList<>();
//
//
//        for (Appointment appointment : appointments) {
//            LisAppointments lisAppointments = new LisAppointments();
//            lisAppointments.setAppointments(appointment);
//            lisAppointments.setPatient(appointment.getUser());
//            lisAppointments.setHistorics(appointment.getUser().getHistorics());
//            List<Attends> attends = appointment.getAttends();
//            List<User> docts = new ArrayList<>();
//            for (Attends attend : attends) {
//                if(attend.getUser().getRoles().contains("DCTR")){
//                    docts.add(attend.getUser());
//                }
//            }
//            lisAppointments.setDoctors(docts);
//            res.add(lisAppointments);
//        }
//
//
//        return null;
//    }

    @GetMapping("/clinic/schedule")
    public ResponseEntity<GeneralResponse> getDoctorAppointments(@RequestParam("date") Date date){
        try {
            User user = userService.findUserAuthenticated();
            if(user == null){
                return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "User not found!");
            }
            List<Attends> attends = attendsService.fidAllUserAndAppointmentRealization(user, date);

            return GeneralResponse.getResponse(HttpStatus.OK, attends);
        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }


}
