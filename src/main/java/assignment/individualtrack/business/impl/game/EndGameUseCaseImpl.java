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
    public EndGameResponse endGame(EndGameRequest request) {
        // Fetch the game entity
        GameEntity gameEntity = gameRepo.findById(request.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found with ID: " + request.getGameId()));

        // Validate game status
        if (gameEntity.getStatus() == GameStatus.COMPLETED) {
            throw new IllegalStateException("Game is already completed.");
        }

        // Calculate score based on correct and incorrect guesses
        int calculatedScore = request.getCorrectGuesses() - request.getIncorrectGuesses();

        if (calculatedScore < 0) {
            calculatedScore = 0;
        }

        // Update game entity with new data
        gameEntity.setCorrectGuesses(request.getCorrectGuesses());
        gameEntity.setWrongGuesses(request.getIncorrectGuesses());
        gameEntity.setTime(request.getTime());
        gameEntity.setScore(calculatedScore); // Use calculated score
        gameEntity.setStatus(request.getStatus());

        // Save updated game entity
        GameEntity updatedGame = gameRepo.save(gameEntity);

        // Build and return the response
        return EndGameResponse.builder()
                .finalScore(updatedGame.getScore())
                .timeTaken(updatedGame.getTime())
                .correctGuesses(updatedGame.getCorrectGuesses())
                .incorrectGuesses(updatedGame.getWrongGuesses())
                .message("Game completed successfully!")
                .build();
    }
}
