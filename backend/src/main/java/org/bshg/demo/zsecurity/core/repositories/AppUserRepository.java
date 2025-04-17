package org.bshg.demo.zsecurity.core.repositories;

import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository< AppUser, Long> {
    Optional< AppUser> findByEmail(String email);
}
