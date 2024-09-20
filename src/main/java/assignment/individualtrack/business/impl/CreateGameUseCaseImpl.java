package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.CreateGameUseCase;
import assignment.individualtrack.domain.CreateGameResponse;
import assignment.individualtrack.domain.CreateGameRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;

@Service
@AllArgsConstructor
public class CreateGameUseCaseImpl implements CreateGameUseCase {
    private final GameRepo gameRepo;
    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        return null;
    }

    public GameEntity saveNewGame(CreateGameRequest request) {
    GameEntity gameEntity = GameEntity.builder()
            .id(request.getId())
            .score(request.getScore())
            .time(request.getTime())
            .build();
    return gameRepo.save(gameEntity);
    }
}
