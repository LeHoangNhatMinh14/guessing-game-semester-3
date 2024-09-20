package assignment.individualtrack.business;

import assignment.individualtrack.domain.CreateGameRequest;
import assignment.individualtrack.domain.CreateGameResponse;

public interface CreateGameUseCase {
    CreateGameResponse createGame(CreateGameRequest createGameRequest);
}
