package assignment.individualtrack;

import assignment.individualtrack.persistence.Role;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.PlayerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlayerRepoIntegrationTest {

    @Autowired
    private PlayerRepo playerRepo;

    @BeforeEach
    void setUp() {
        playerRepo.deleteAll();
    }

    @Test
    void testExistsByName() {
        // Given
        PlayerEntity player = PlayerEntity.builder()
                .name("JohnDoe")
                .password("password123")
                .highscore(100)
                .role(Role.USER)
                .build();
        playerRepo.save(player);

        // When
        boolean exists = playerRepo.existsByName("JohnDoe");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void testFindById() {
        // Given
        PlayerEntity player = PlayerEntity.builder()
                .name("JaneDoe")
                .password("securePass")
                .highscore(150)
                .role(Role.USER)
                .build();
        PlayerEntity savedPlayer = playerRepo.save(player);

        // When
        Optional<PlayerEntity> foundPlayer = playerRepo.findById(savedPlayer.getId());

        // Then
        assertThat(foundPlayer).isPresent();
        assertThat(foundPlayer.get().getName()).isEqualTo("JaneDoe");
        assertThat(foundPlayer.get().getHighscore()).isEqualTo(150);
        assertThat(foundPlayer.get().getPassword()).isEqualTo("securePass");
        assertThat(foundPlayer.get().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void testDeleteById() {
        // Given
        PlayerEntity player = PlayerEntity.builder()
                .name("DeleteMe")
                .password("deletePass")
                .highscore(200)
                .role(Role.USER)
                .build();
        PlayerEntity savedPlayer = playerRepo.save(player);

        // When
        playerRepo.deleteById(savedPlayer.getId());
        Optional<PlayerEntity> deletedPlayer = playerRepo.findById(savedPlayer.getId());

        // Then
        assertThat(deletedPlayer).isNotPresent();
    }

    @Test
    void testFindByName() {
        // Given
        PlayerEntity player = PlayerEntity.builder()
                .name("FindMe")
                .password("findPass")
                .highscore(300)
                .role(Role.USER)
                .build();
        playerRepo.save(player);

        // When
        Optional<PlayerEntity> foundPlayer = playerRepo.findByName("FindMe");

        // Then
        assertThat(foundPlayer).isPresent();
        assertThat(foundPlayer.get().getName()).isEqualTo("FindMe");
        assertThat(foundPlayer.get().getHighscore()).isEqualTo(300);
        assertThat(foundPlayer.get().getPassword()).isEqualTo("findPass");
        assertThat(foundPlayer.get().getRole()).isEqualTo(Role.USER);
    }
}