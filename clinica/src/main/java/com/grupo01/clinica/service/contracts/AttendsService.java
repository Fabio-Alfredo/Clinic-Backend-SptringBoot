package com.grupo01.clinica.service.contracts;

import com.grupo01.clinica.domain.entities.Attends;
import com.grupo01.clinica.domain.entities.User;


import java.util.Date;
import java.util.List;

public interface AttendsService {
    List<Attends>fidAllUserAndAppointmentRealization(User user, Date realization);
}
