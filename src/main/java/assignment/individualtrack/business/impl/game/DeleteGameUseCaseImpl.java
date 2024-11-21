package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.intefaces.DeleteGameUseCase;
import assignment.individualtrack.persistence.GameRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteGameUseCaseImpl implements DeleteGameUseCase {
    private final GameRepo gameRepo;

    @Override
    public void deleteGame(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }
        // Proceed with deletion if the ID is not null.
        gameRepo.deleteById(gameId);
    }

}
