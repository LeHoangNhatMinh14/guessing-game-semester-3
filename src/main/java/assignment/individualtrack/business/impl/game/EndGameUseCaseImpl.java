package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.exception.GameNotFoundException;
import assignment.individualtrack.business.intefaces.EndGameUseCase;
import assignment.individualtrack.domain.Game.EndGameRequest;
import assignment.individualtrack.domain.Game.EndGameResponse;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EndGameUseCaseImpl implements EndGameUseCase {
    private final GameRepo gameRepo;

    @Override
    public EndGameResponse endGame(EndGameRequest endGameRequest) {
        // Validate input
        if (endGameRequest.getGameId() < 0) {
            throw new IllegalArgumentException("Invalid game ID.");
        }

        GameEntity gameEntity = gameRepo.findById(endGameRequest.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found with ID: " + endGameRequest.getGameId()));

        if (gameEntity.getStatus() == GameStatus.COMPLETED) {
            throw new IllegalStateException("Game has already been completed.");
        }

        // Update game entity with request data
        gameEntity.setCorrectGuesses(endGameRequest.getCorrectGuesses());
        gameEntity.setWrongGuesses(endGameRequest.getIncorrectGuesses());
        gameEntity.setScore(endGameRequest.getScore());
        gameEntity.setTime(endGameRequest.getTime());
        gameEntity.setStatus(GameStatus.COMPLETED); // Finalize game status

        // Save updated game entity
        GameEntity updatedGame = gameRepo.save(gameEntity);

        // Build and return response
        return EndGameResponse.builder()
                .finalScore(updatedGame.getScore())
                .timeTaken(updatedGame.getTime())
                .correctGuesses(updatedGame.getCorrectGuesses())
                .incorrectGuesses(updatedGame.getWrongGuesses())
                .message("Game completed successfully!")
                .build();
    }
}

