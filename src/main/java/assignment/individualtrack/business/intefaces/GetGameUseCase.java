package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Game.GetGameRequest;
import assignment.individualtrack.domain.Game.GetGameResponse;

public interface GetGameUseCase {
    GetGameResponse getGame(GetGameRequest request);
}
