package se.iths.axel.projektuppgiftwebshop.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.axel.projektuppgiftwebshop.model.AppUser;
import se.iths.axel.projektuppgiftwebshop.repository.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserRepository repository;
    private static final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Använder här tills vi har en Bean i SecurityConfig
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
    }

    public AppUser registerNewAppUser(AppUser appUser) {
        String username = appUser.getUsername();

        if (!username.matches(regex)) {
            throw new IllegalArgumentException("Email not valid");
        }

        if (repository.existsAppUsersByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }

        String encodedPassword = encoder.encode(appUser.getPassword());

        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(username);
        newAppUser.setPassword(encodedPassword);
        newAppUser.setRole("USER");

        return repository.save(newAppUser);
    }
}
