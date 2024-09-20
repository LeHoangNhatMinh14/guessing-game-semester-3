package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.CreatePlayerUseCase;
import assignment.individualtrack.domain.CreatePlayerRequest;
import assignment.individualtrack.domain.CreatePlayerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;

@Service
@AllArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {
    private final PlayerRepo playerRepo;

    @Override
    public CreatePlayerResponse createPlayer(CreatePlayerRequest player) {
        if (playerRepo.existsbyName(player.getName())) {
            return null;
        }

        PlayerEntity playerEntity = saveNewPlayer(player);
        return CreatePlayerResponse.builder()
                .playerId(playerEntity.getId())
                .build();
    }

    private PlayerEntity saveNewPlayer(CreatePlayerRequest request) {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .name(request.getName())
                .build();

        return playerRepo.save(newPlayer);
    }
}
