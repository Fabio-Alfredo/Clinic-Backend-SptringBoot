package com.grupo01.clinica.service.contracts;


import com.grupo01.clinica.domain.entities.Specialty;



public interface SpecialityService {
    Specialty findByName(String name);
}
