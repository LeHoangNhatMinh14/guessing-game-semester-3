package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.exception.GameNotFoundException;
import assignment.individualtrack.business.intefaces.EndGameUseCase;
import assignment.individualtrack.domain.Game.EndGameRequest;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EndGameUseCaseImpl implements EndGameUseCase {
    private final GameRepo gameRepo;

    public void endGame(EndGameRequest endGameRequest) {
        // Fetch the game entity by gameId
        GameEntity gameEntity = gameRepo.findbyID(endGameRequest.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found"));

        // Update the game with the final score, time, and status
        gameEntity.setScore(endGameRequest.getScore());
        gameEntity.setTime(endGameRequest.getTime());
        gameEntity.setStatus(GameStatus.COMPLETED);

        // Save the updated game entity
        gameRepo.save(gameEntity);
    }
}
