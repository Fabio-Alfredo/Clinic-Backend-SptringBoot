package com.grupo01.clinica.service.impl;

import com.grupo01.clinica.domain.dtos.req.RecordDTO;
import com.grupo01.clinica.domain.entities.Historic;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.repositorie.HistoryRepository;
import com.grupo01.clinica.service.contracts.HistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImp implements HistoryService {
    private final HistoryRepository historyRepository;
    private final ModelMapper modelMapper;

    public HistoryServiceImp(HistoryRepository historyRepository, ModelMapper modelMapper) {
        this.historyRepository = historyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Historic createHistory(RecordDTO req, User user) {
        Historic historic = modelMapper.map(req, Historic.class);
        historic.setCreteAt(new Date().from(new Date().toInstant()));
        historic.setUser(user);
        return historyRepository.save(historic);
    }

    @Override
    public List<Historic> allHistory() {
        return historyRepository.findAll();
    }

    @Override
    public List<Historic> findByPatientAndDateRange( User user, Date start, Date end) {
        return historyRepository.findAllByUserAndCreteAtBetween(user, start, end);
    }

    @Override
    public List<Historic> findByPatient(User user) {
        return historyRepository.findAllByUser(user);
    }


}
