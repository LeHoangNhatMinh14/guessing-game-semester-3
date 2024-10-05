package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Game.GetPlayerGameHistoryRequest;
import assignment.individualtrack.domain.Game.GetPlayerGameHistoryResponse;

public interface GetPlayerGameHistoryUseCase {
    GetPlayerGameHistoryResponse getPlayerGameHistory(GetPlayerGameHistoryRequest request);
}
