package com.example.authdemo.config;

import com.example.authdemo.entity.Role;
import com.example.authdemo.entity.User;
import com.example.authdemo.repository.RoleRepository;
import com.example.authdemo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            if (userRepository.findByEmail("admin").isEmpty()){
                Role role = roleRepository.findByName("ADMIN")
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setName("ADMIN");
                            return roleRepository.save(newRole);
                        });

                User user = User.builder()
                        .email("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Set.of(role))
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}

