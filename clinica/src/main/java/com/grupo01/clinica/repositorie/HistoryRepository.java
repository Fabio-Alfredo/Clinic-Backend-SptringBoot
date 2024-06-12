package com.grupo01.clinica.repositorie;

import com.grupo01.clinica.domain.entities.Historic;
import com.grupo01.clinica.domain.entities.User;
import com.grupo01.clinica.service.contracts.HistoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<Historic, UUID> {
    List<Historic>findAllByUserAndDateBetween(User user, Date start, Date end);
}
