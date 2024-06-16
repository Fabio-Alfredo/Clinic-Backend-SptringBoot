package com.grupo01.clinica.controller;

import com.grupo01.clinica.domain.dtos.req.AppointmentDTO;
import com.grupo01.clinica.domain.dtos.req.ApprovedAppointmentDTO;
import com.grupo01.clinica.domain.dtos.req.PrescriptionCreateDTO;
import com.grupo01.clinica.domain.dtos.res.*;
import com.grupo01.clinica.domain.entities.*;
import com.grupo01.clinica.service.contracts.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final RoleService roleService;
    private final PrescriptionService prescriptionService;
    private final AttendsService attendsService;
    private final HistoryService historyService;
    private final SpecialityService specialityService;

    public AppointmentController(UserService userService, AppointmentService appointmentService, RoleService roleService, PrescriptionService prescriptionService, AttendsService attendsService, HistoryService historyService, SpecialityService specialityService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.roleService = roleService;
        this.prescriptionService = prescriptionService;
        this.attendsService = attendsService;
        this.historyService = historyService;
        this.specialityService = specialityService;
    }

    @PostMapping("/request")
    public ResponseEntity<GeneralResponse>createAppointment(@RequestBody @Valid AppointmentDTO req) {
        try {
            User user = userService.findUserAuthenticated();
            if (user == null) {
                return GeneralResponse.getResponse(HttpStatus.FOUND, "User not found!");
            }
            appointmentService.createAppointment(req, user);

            return GeneralResponse.getResponse(HttpStatus.OK, "Cita agendada con exito!");

        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<GeneralResponse>getPendingAppointments(){
        try {
            List<Appointment> res = appointmentService.findAllByStatus("PENDING");
            return GeneralResponse.getResponse(HttpStatus.OK, res);
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
        User user = userService.findUserAuthenticated();
        try {

            Appointment appointment = appointmentService.findById(id);
            if(appointment == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Appointment not found!");
            }

            //valida si el doctor es el doctor de la cita
//            if(!appointmentService.isDoctorAndAppointment(user, appointment)){
//                return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Unauthorized!");
//            }

            appointmentService.finishAppointment(appointment);
            return GeneralResponse.getResponse(HttpStatus.OK, "Appointment finished!");
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @PostMapping("/clinic/prescription")
    @PreAuthorize("hasAnyAuthority( 'DCTR')")
    public ResponseEntity<GeneralResponse>prescriptionAppointment(@RequestBody PrescriptionCreateDTO req){
        try {
            Appointment appointment = appointmentService.findById(req.getAppointment());
            User doc = userService.findUserAuthenticated();
            if(appointment == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Appointment not found!");
            }
            //Validar si el user es el doctor de la cita
//            if(!appointmentService.isDoctorAndAppointment(doc, appointment)){
//                return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "Unauthorized!");
//            }
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
        public ResponseEntity<GeneralResponse> getPatientAppointments( @RequestParam (required = false, name = "phase") String phase){
        try {
            User user = userService.findUserAuthenticated();
            return GeneralResponse.getResponse(HttpStatus.OK, appointmentService.getAppointments(user, phase));
        }catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/clinic/denied")
    public ResponseEntity<GeneralResponse> deniedAppointment(@RequestParam("id") UUID id){
        try {
            Appointment appointment = appointmentService.findById(id);
            if(appointment == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Appointment not found!");
            }
            if(!appointment.getStatus().equals("PENDING")){
                return GeneralResponse.getResponse(HttpStatus.FOUND, "Appointment already denied or approved!");
            }
            appointment.setStatus("DENIED");
            appointmentService.saveAppointment(appointment);
            return GeneralResponse.getResponse(HttpStatus.OK, "Appointment denied!");
        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @GetMapping("/clinic/schedule")
    public ResponseEntity<GeneralResponse> getDoctorAppointments(@RequestParam("date") String date){
        try {

            User user = userService.findUserAuthenticated();
            if(user == null){
                return GeneralResponse.getResponse(HttpStatus.UNAUTHORIZED, "User not found!");
            }
            SimpleDateFormat annotation = new SimpleDateFormat("yyyy/MM/dd");
            Date start = annotation.parse(date);
            Date end = annotation.parse(date);
            start.setHours(0);
            start.setMinutes(0);
            start.setSeconds(0);
            end.setHours(23);
            end.setMinutes(59);

            List<Attends> appointment = attendsService.findAllUserAndAppointmentDate(user, start, end);
            List<AttendsResponseDTO> schedule = new ArrayList<>();
            for(Attends a: appointment){
                AttendsResponseDTO attendsResponseDTO = new AttendsResponseDTO();
                attendsResponseDTO.setPrincipalDoc(a.getUser());

                AppointmentDoctorDTO appointmentDoctorDTO = new AppointmentDoctorDTO();
                appointmentDoctorDTO.setAppointment(a.getAppointment());

                List<ScheduleAppointmentDTO> doctors = new ArrayList<>();
                for(Attends attends: a.getAppointment().getAttends()){
                    ScheduleAppointmentDTO scheduleAppointmentDTO = new ScheduleAppointmentDTO();
                    ScheduleDocDTO doctor =  new ScheduleDocDTO(attends.getUser().getId(), attends.getUser().getName(), attends.getUser().getEmail());
                    scheduleAppointmentDTO.setDoctor(doctor);
                    scheduleAppointmentDTO.setSpeciality(attends.getSpecialty());
                    doctors.add(scheduleAppointmentDTO);

                }
                appointmentDoctorDTO.setDoctors(doctors);

                List<Historic> userHistory = historyService.findByPatient(a.getAppointment().getUser());
                userHistory.sort(Comparator.comparing(Historic::getDate).reversed());
                appointmentDoctorDTO.setHistorics(userHistory);
                attendsResponseDTO.setAppointments(appointmentDoctorDTO);
                schedule.add(attendsResponseDTO);
            }
            return GeneralResponse.getResponse(HttpStatus.OK, schedule);

        }catch (Exception e){
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!");
        }
    }

    @PostMapping("/approve")
//    @PreAuthorize("hasAnyAuthority('ADMN', 'ASST')")
    public ResponseEntity<GeneralResponse>approveAppointment(@RequestBody ApprovedAppointmentDTO  req){
        try {
            Appointment appointment = appointmentService.findById(req.getAppointmentId());
            if(appointment == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Appointment not found!");
            }
            User paciente = userService.findBiId(req.getUserId());
            if (paciente == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "User not found!");
            }
            if(!req.getIsAccepted()){
                appointment.setStatus("DENIED");
                appointmentService.saveAppointment(appointment);

                return GeneralResponse.getResponse(HttpStatus.FOUND, "Appointment not accepted!");
            }

            Date estimated = new Date(req.getRealization().getTime()+req.getDuration() * 60000L);
            appointment.setAccepted(req.getIsAccepted());
            appointment.setFinalization(estimated);
            appointment.setRealization(req.getRealization());
            appointment.setSchedulEndDate(req.getSchedulEndDate());
            appointment.setStatus("APPROVED");

            Role role = roleService.getRoleById("PCTE");
            if (role == null){
                return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Role not found!");
            }
            userService.updateUserRol(paciente, role);
            for (String doctorEmail: req.getDoctorEmail()){
                User doctor = userService.findByemail(doctorEmail);

                Role roleDoctor = roleService.getRoleById("DCTR");

                if(doctor == null || !doctor.getRoles().contains(roleDoctor)){
                    return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Doctor not found!");
                }
                boolean IsDoctorAvailable = appointmentService.isDoctorAndAvailable(doctor, req.getRealization(), estimated);
                if(!IsDoctorAvailable){
                    return GeneralResponse.getResponse(HttpStatus.FOUND, "Doctor not available!");
                }
                for(String specialityName: req.getSpecialists()){
                    Specialty specialty = specialityService.findByName(specialityName);
                    if(specialty == null){
                        return GeneralResponse.getResponse(HttpStatus.NOT_FOUND, "Speciality not found!");
                    }else{
                        Attends attends = new Attends();
                        attends.setAppointment(appointment);
                        attends.setUser(doctor);
                        attends.setSpecialty(specialty);
                        attendsService.saveAttend(attends);
                    }
                }
                appointmentService.saveAppointment(appointment);
            }
            return GeneralResponse.getResponse(HttpStatus.OK, "Appointment approved!");

        } catch (Exception e) {
            return GeneralResponse.getResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!" + e.getMessage());
        }
    }

}
