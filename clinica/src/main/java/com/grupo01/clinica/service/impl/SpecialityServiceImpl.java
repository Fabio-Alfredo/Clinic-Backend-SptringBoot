package com.grupo01.clinica.service.impl;

import com.grupo01.clinica.domain.entities.Specialty;
import com.grupo01.clinica.repositorie.SpecialityRepository;
import com.grupo01.clinica.service.contracts.SpecialityService;
import org.springframework.stereotype.Service;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Specialty findByName(String name) {
        return specialityRepository.findByName(name);
    }
}
