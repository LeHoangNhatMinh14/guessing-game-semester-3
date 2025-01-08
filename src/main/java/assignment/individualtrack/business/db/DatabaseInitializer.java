package assignment.individualtrack.business.db;


import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner{
    private final PlayerRepo playerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if database is empty
        if (playerRepo.count() == 0) {
            // Initialize NormalUser
            PlayerEntity normalUser = PlayerEntity.builder()
                    .name("NormalUser")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER) // USER role
                    .highscore(0) // Default high score
                    .build();
            playerRepo.save(normalUser);

            // Initialize AdminUser
            PlayerEntity adminUser = PlayerEntity.builder()
                    .name("AdminUser")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ADMIN) // ADMIN role
                    .highscore(0) // Default high score
                    .build();
            playerRepo.save(adminUser);

            System.out.println("Database initialized with default users.");
        } else {
            System.out.println("Database already contains users. Skipping initialization.");
        }
    }
}
