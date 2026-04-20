package se.iths.axel.projektuppgiftwebshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.axel.projektuppgiftwebshop.model.AppUser;
import se.iths.axel.projektuppgiftwebshop.repository.AppUserRepository;
import se.iths.sofia.webshopmailservice.MailService;

@Service
public class AppUserService {

    private final AppUserRepository repository;
    private static final String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final PasswordEncoder encoder;

    private final MailService mailService;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder, MailService mailService) {
        this.repository = repository;
        this.encoder = encoder;
        this.mailService = mailService;
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
        boolean consent = appUser.getConsent();

        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(username);
        newAppUser.setPassword(encodedPassword);
        newAppUser.setRole("USER");
        newAppUser.setConsent(consent);

        return repository.save(newAppUser);
    }

    public AppUser findByUsername(String username) {
        AppUser user = repository.findAppUsersByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }

    public void sendUserInfoByEmail(String username) {
        AppUser user = findByUsername(username);

        String body = "Här är dina uppgifter:\n\n" +
                "E-post: " + user.getUsername() + "\n" +
                "Roll: " + user.getRole() + "\n" +
                "Samtycke: " + user.getConsent();

        mailService.sendUserDataMail(user.getUsername(), body);
    }

    public void deleteUserByUsername(String username) {
        AppUser user = findByUsername(username);
        repository.delete(user);
    }
}

