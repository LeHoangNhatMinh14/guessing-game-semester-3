package assignment.individualtrack.business.impl.player;

import assignment.individualtrack.business.exception.PlayerNotFoundException;
import assignment.individualtrack.business.intefaces.DeletePlayerUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.PlayerRepo;

@Service
@AllArgsConstructor
public class DeletePlayerUseCaseImpl implements DeletePlayerUseCase {
    private final PlayerRepo playerRepo;

    @Override
    public void deletePlayer(Long playerId) {
        if (playerId == null) {
            throw new PlayerNotFoundException("Player ID cannot be null");
        }

        // Check if the player exists in the repository
        if (!playerRepo.existsById(playerId)) {
            throw new PlayerNotFoundException("Player with ID " + playerId + " does not exist");
        }

        // If player exists, delete
        playerRepo.deleteById(playerId);
    }
}
