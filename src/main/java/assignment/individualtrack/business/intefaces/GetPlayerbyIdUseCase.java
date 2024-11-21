package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Player.GetPlayerRequest;
import assignment.individualtrack.domain.Player.GetPlayerResponse;

public interface GetPlayerbyIdUseCase {
    GetPlayerResponse getPlayer(GetPlayerRequest request);
}
