package cr.ac.una.productsapplication.services;

import cr.ac.una.productsapplication.models.AppUser;
import cr.ac.una.productsapplication.repositories.IAppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IAppUserRepository repository;

    public CustomUserDetailsService(IAppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder().username(user.getUsername()).password(user.getPassword()).disabled(!user.isEnabled()).authorities(user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet())).build();
    }
}
