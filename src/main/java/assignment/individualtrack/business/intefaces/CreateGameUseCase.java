package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;

public interface CreateGameUseCase {
    StartGameResponse createGame(StartGameRequest startGameRequest);
}
