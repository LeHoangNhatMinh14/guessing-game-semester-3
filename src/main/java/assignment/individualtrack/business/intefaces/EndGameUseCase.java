package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Game.EndGameRequest;
import assignment.individualtrack.domain.Game.EndGameResponse;

public interface EndGameUseCase {
    EndGameResponse endGame(EndGameRequest endGameRequest);
}
