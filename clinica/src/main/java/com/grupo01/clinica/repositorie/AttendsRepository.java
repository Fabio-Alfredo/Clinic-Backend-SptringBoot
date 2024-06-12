package com.grupo01.clinica.repositorie;

import com.grupo01.clinica.domain.entities.Attends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendsRepository extends JpaRepository<Attends, UUID> {

}
