package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.DeletePlayerUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.PlayerRepo;

@Service
@AllArgsConstructor
public class DeletePlayerUseCaseImpl implements DeletePlayerUseCase {
    private final PlayerRepo playerRepo;

    @Override
    public void deletePlayer(long playerId) {
        this.playerRepo.deletebyID(playerId);
    }
}
