package assignment.individualtrack.business.db;

import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final PlayerRepo playerRepo;
    private final ThemeRepo themeRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Initialize default users
        if (playerRepo.count() == 0) {
            initializeDefaultUsers();
        } else {
            System.out.println("Database already contains users. Skipping user initialization.");
        }

        // Initialize default themes
        if (!themeRepo.existsByName("Pokemon")) {
            ThemeEntity pokemonTheme = ThemeEntity.builder()
                    .name("Pokemon")
                    .build();
            themeRepo.save(pokemonTheme);
            System.out.println("Pokemon theme added to the database.");
        } else {
            System.out.println("Pokemon theme already exists in the database.");
        }
    }

    private void initializeDefaultUsers() {
        PlayerEntity normalUser = PlayerEntity.builder()
                .name("NormalUser")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .highscore(0)
                .build();
        playerRepo.save(normalUser);

        PlayerEntity adminUser = PlayerEntity.builder()
                .name("AdminUser")
                .password(passwordEncoder.encode("password"))
                .role(Role.ADMIN)
                .highscore(0)
                .build();
        playerRepo.save(adminUser);

        System.out.println("Database initialized with default users.");
    }
}