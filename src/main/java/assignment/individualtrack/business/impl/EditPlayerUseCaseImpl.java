package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.EditPlayerUseCase;
import assignment.individualtrack.business.exception.InvalidPlayerException;
import assignment.individualtrack.domain.EditPlayerRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EditPlayerUseCaseImpl implements EditPlayerUseCase {
    private final PlayerRepo playerRepo;

    @Override
    public void editPlayer(EditPlayerRequest player) {
        Optional<PlayerEntity> playerOptional = playerRepo.findbyID(player.getId());
        if (playerOptional.isEmpty()) {
            throw new InvalidPlayerException("STUDENT_ID_INVALID");
        }

        PlayerEntity playerEntity = playerOptional.get();
        updateFields(player, playerEntity);
    }

    private void updateFields(EditPlayerRequest request, PlayerEntity player) {
        player.setName(request.getName());
        player.setHighscore(request.getHighscore());

        playerRepo.save(player);
    }
}
