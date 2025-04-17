package org.bshg.demo.zconfigs.auditor;

import org.bshg.demo.zsecurity.core.entities.AppUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "auditorAware")
public class ApplicationAuditorAware implements AuditorAware< Long> {
    @Override
    public Optional< Long> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null ||
                !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken
        ) return Optional.empty();
        var user = (AppUser) auth.getPrincipal();
        return Optional.ofNullable(user.getId());
    }
}
