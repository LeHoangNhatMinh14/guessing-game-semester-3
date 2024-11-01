package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.exception.GameNotFoundException;
import assignment.individualtrack.business.intefaces.GetGameUseCase;
import assignment.individualtrack.domain.Game.GetGameRequest;
import assignment.individualtrack.domain.Game.GetGameResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetGameUseCaseImpl implements GetGameUseCase {
    private final GameRepo gameRepo;

    @Override
    public GetGameResponse getGame(GetGameRequest request){
        GameEntity gameEntity = gameRepo.findById((int)request.getGameID())
                .orElseThrow(() -> new GameNotFoundException("Game with ID " + request.getGameID() + " not found"));

        // Map GameEntity to GetGameResponse
        return GetGameResponse.builder()
                .id(gameEntity.getId())
                .score(gameEntity.getScore())
                .time(gameEntity.getTime())
                .status(gameEntity.getStatus())  // Assuming status is being tracked
                .build();
    }
}
