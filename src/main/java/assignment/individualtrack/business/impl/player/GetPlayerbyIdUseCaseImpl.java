package assignment.individualtrack.business.impl.player;

import assignment.individualtrack.business.exception.InvalidPlayerException;
import assignment.individualtrack.business.intefaces.GetPlayerbyIdUseCase;
import assignment.individualtrack.domain.Player.GetPlayerRequest;
import assignment.individualtrack.domain.Player.GetPlayerResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetPlayerbyIdUseCaseImpl implements GetPlayerbyIdUseCase {

    private final PlayerRepo playerRepo;

    @Override
    public GetPlayerResponse getPlayer(GetPlayerRequest request) {
        PlayerEntity playerEntity = playerRepo.findById(request.getPlayerId())
                .orElseThrow(() -> new InvalidPlayerException("Player not found"));

        return GetPlayerResponse.builder()
                .playerId(playerEntity.getId())
                .name(playerEntity.getName())
                .highScore(playerEntity.getHighscore())
                .build();
    }
}
