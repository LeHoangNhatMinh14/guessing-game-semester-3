package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.intefaces.CreateGameUseCase;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateGameUseCaseImpl implements CreateGameUseCase {
    private final GameRepo gameRepo;
    private final PlayerRepo playerRepo;
    private final ThemeRepo themeRepo;

    @Override
    public StartGameResponse createGame(StartGameRequest startGameRequest) {
        // Validate player ID
        if (startGameRequest.getPlayerID() == null || startGameRequest.getPlayerID() <= 0) {
            throw new IllegalArgumentException("Invalid or missing player ID.");
        }

        // Validate theme ID
        if (startGameRequest.getThemeID() == null || startGameRequest.getThemeID() <= 0) {
            throw new IllegalArgumentException("Invalid or missing theme ID.");
        }

        // Fetch PlayerEntity and ThemeEntity
        PlayerEntity playerEntity = playerRepo.findById(startGameRequest.getPlayerID())
                .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + startGameRequest.getPlayerID()));

        ThemeEntity themeEntity = themeRepo.findById(startGameRequest.getThemeID())
                .orElseThrow(() -> new IllegalArgumentException("Theme not found with ID: " + startGameRequest.getThemeID()));

        // Create and save new game entity
        GameEntity newGame = GameEntity.builder()
                .player(playerEntity)
                .theme(themeEntity)
                .score(0)
                .time(0)
                .correctGuesses(0)
                .wrongGuesses(0)
                .status(GameStatus.IN_PROGRESS)
                .playedAt(LocalDateTime.now()) // Set the current time
                .build();

        GameEntity savedGame = gameRepo.save(newGame);

        // Build and return response
        return StartGameResponse.builder()
                .gameId(savedGame.getId())
                .playerId(savedGame.getPlayer().getId())
                .build();
    }
}


