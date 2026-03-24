package cr.ac.una.productsapplication.config;

import cr.ac.una.productsapplication.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider()).authorizeHttpRequests(auth -> auth.requestMatchers("/css/**", "/js/**", "/images/**").permitAll().requestMatchers("/", "/login", "/access-denied").permitAll().requestMatchers("/products", "/products/search", "/products/*").hasAnyRole("USER", "ADMIN").requestMatchers("/products/new", "/products/*/edit", "/products/*/delete", "/products/save", "/products/*/update").hasRole("ADMIN").requestMatchers("/categories/**", "/tags/**").hasRole("ADMIN").anyRequest().authenticated()).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error").permitAll()).logout(logout -> logout.logoutSuccessUrl("/login?logout")).exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));

        return http.build();
    }
}
