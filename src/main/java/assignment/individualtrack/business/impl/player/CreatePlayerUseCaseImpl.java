package assignment.individualtrack.business.impl.player;

import assignment.individualtrack.business.intefaces.CreatePlayerUseCase;
import assignment.individualtrack.domain.Player.CreatePlayerRequest;
import assignment.individualtrack.domain.Player.CreatePlayerResponse;
import assignment.individualtrack.persistence.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Service
@AllArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {
    private final PlayerRepo playerRepo;
    private final PasswordEncoder passwordEncoder;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public CreatePlayerResponse createPlayer(CreatePlayerRequest player) {
        if (player == null) {
            throw new IllegalArgumentException("Player request cannot be null");
        }
        if (player.getName() == null || player.getName().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (player.getPassword() == null || player.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (player.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        if (playerRepo.existsByName(player.getName())) {
            return null;
        }

        PlayerEntity playerEntity = saveNewPlayer(player);
        return CreatePlayerResponse.builder()
                .playerId(playerEntity.getId())
                .build();
    }

    private PlayerEntity saveNewPlayer(CreatePlayerRequest request) {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword())) // Encrypt the password
                .role(Role.USER)
                .highscore(0)  // Set highScore to 0 for new players
                .build();

        return playerRepo.save(newPlayer);
    }
}
