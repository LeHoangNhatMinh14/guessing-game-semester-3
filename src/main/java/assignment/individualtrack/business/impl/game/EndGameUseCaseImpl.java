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
        GameEntity gameEntity = gameRepo.findById(endGameRequest.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found"));

        if (gameEntity.getStatus() == GameStatus.COMPLETED) {
            throw new IllegalStateException("Game has already been completed.");
        }

        gameEntity.setScore(endGameRequest.getScore());
        gameEntity.setTime(endGameRequest.getTime());
        gameEntity.setStatus(GameStatus.COMPLETED);

        // Save the updated game entity
        gameRepo.save(gameEntity);

        // Build and return EndGameResponse
        return EndGameResponse.builder()
                .finalScore(gameEntity.getScore())
                .timeTaken(gameEntity.getTime())
                .correctGuesses(endGameRequest.getCorrectGuesses())
                .incorrectGuesses(endGameRequest.getIncorrectGuesses())
                .message("Game completed successfully!")
                .build();
    }
}
