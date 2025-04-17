package org.bshg.demo.zsecurity.core.services.user;

import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.bshg.demo.zsecurity.core.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository userRepository;

    public AppUserServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }
}