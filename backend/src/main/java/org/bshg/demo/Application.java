package org.bshg.demo;

import org.bshg.demo.zsecurity.core.entities.Role;
import org.bshg.demo.zsecurity.core.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                var role = new Role();
                role.setName("USER");
                roleRepository.save(role);
            }
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                var role = new Role();
                role.setName("ADMIN");
                roleRepository.save(role);
            }
        };
    }
}
