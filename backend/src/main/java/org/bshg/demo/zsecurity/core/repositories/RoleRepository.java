package org.bshg.demo.zsecurity.core.repositories;


import org.bshg.demo.zsecurity.core.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository< Role, Long> {
    Optional< Role> findByName(String email);
}
