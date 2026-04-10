package se.iths.axel.projektuppgiftwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.axel.projektuppgiftwebshop.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUsersByUsername(String username);

    boolean existsAppUsersByUsername(String username);
}
