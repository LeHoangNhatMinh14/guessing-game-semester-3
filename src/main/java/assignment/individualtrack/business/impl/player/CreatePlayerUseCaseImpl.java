package assignment.individualtrack.business.impl.player;

import assignment.individualtrack.business.intefaces.CreatePlayerUseCase;
import assignment.individualtrack.domain.Player.CreatePlayerRequest;
import assignment.individualtrack.domain.Player.CreatePlayerResponse;
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
        System.out.println("CreatePlayerRequest: " + player);
        if (playerRepo.existsByName(player.getName())) {
            return null;
        }
        System.out.println("CreatePlayerResponse: " + player);
        PlayerEntity playerEntity = saveNewPlayer(player);
        return CreatePlayerResponse.builder()
                .playerId(playerEntity.getId())
                .build();
    }

    private PlayerEntity saveNewPlayer(CreatePlayerRequest request) {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .name(request.getName())
                .password(request.getPassword())
                .highscore(0)  // Set highScore to 0 for new players
                .build();

        return playerRepo.save(newPlayer);
    }
}
