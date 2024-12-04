package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.intefaces.CreateGameUseCase;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateGameUseCaseImpl implements CreateGameUseCase {
    private final GameRepo gameRepo;
    private final PlayerRepo playerRepo; // Inject the Player repository to fetch player entities

    @Override
    public StartGameResponse createGame(StartGameRequest startGameRequest) {
        // Validate that playerID is not null
        if (startGameRequest.getPlayerID() == null) {
            throw new IllegalArgumentException("Player ID cannot be null");
        }

        // Fetch the PlayerEntity by playerID
        PlayerEntity player = playerRepo.findById(startGameRequest.getPlayerID())
                .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + startGameRequest.getPlayerID()));

        // Create a new GameEntity and associate it with the fetched player
        GameEntity newGame = GameEntity.builder()
                .player(player) // Associate with player entity
                .score(0) // Initialize score to 0
                .time(0)  // Initialize time to 0
                .status(GameStatus.IN_PROGRESS) // Set the initial game status
                .build();

        // Save the new game to the database
        GameEntity savedGame = gameRepo.save(newGame);

        // Return the response with gameId and playerId
        return StartGameResponse.builder()
                .gameId(savedGame.getId()) // Generated game ID from the database
                .playerId(savedGame.getPlayer().getId()) // Player ID from the saved game
                .build();
    }
}
