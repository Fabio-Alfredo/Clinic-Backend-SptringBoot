package com.grupo01.clinica.service.contracts;

import com.grupo01.clinica.domain.entities.Role;
import com.grupo01.clinica.domain.entities.User;

import java.util.List;

public interface RoleService {
    Role getRoleById(String id);
}
