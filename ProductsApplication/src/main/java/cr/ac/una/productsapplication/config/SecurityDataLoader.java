package cr.ac.una.productsapplication.config;

import cr.ac.una.productsapplication.models.AppUser;
import cr.ac.una.productsapplication.models.Role;
import cr.ac.una.productsapplication.repositories.IAppUserRepository;
import cr.ac.una.productsapplication.repositories.IRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
public class SecurityDataLoader {
    @Bean
    CommandLineRunner loadSecurityData(IRoleRepository roleRepository, IAppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

            Role roleUser = roleRepository.findByName("ROLE_USER").orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("Admin123*"));
                admin.setEnabled(true);
                admin.setRoles(new HashSet<>());
                admin.getRoles().add(roleAdmin);
                admin.getRoles().add(roleUser);
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                AppUser user = new AppUser();
                user.setUsername("user");
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("User123*"));
                user.setEnabled(true);
                user.setRoles(new HashSet<>());
                user.getRoles().add(roleUser);
                userRepository.save(user);
            }
        };
    }
}