package org.bshg.demo.zsecurity.core.services.user;

import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AppUserService extends UserDetailsService {
    Optional<AppUser> findById(Long id);
}