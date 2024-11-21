package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.intefaces.CreateGameUseCase;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;

@Service
@AllArgsConstructor
public class CreateGameUseCaseImpl implements CreateGameUseCase {
    private final GameRepo gameRepo;
    @Override
    public StartGameResponse createGame(StartGameRequest startGameRequest) {
        // Check if playerID is null
        if (startGameRequest.getPlayerID() == null) {
            throw new IllegalArgumentException("Player ID cannot be null");
        }

        // Save the new game with initial values (score and time set to 0)
        GameEntity newGame = GameEntity.builder()
                .id(startGameRequest.getPlayerID()) // Only playerID is necessary when starting
                .score(0) // Initialize score to 0
                .time(0)  // Initialize time to 0
                .build();

        GameEntity savedGame = gameRepo.save(newGame);

        // Return the response with gameId and playerId
        return StartGameResponse.builder()
                .gameId(savedGame.getId())
                .playerId(savedGame.getId())  // It looks like playerId should match the playerId in the request
                .build();
    }

}
