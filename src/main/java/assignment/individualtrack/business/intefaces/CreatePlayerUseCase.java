package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Player.CreatePlayerRequest;
import assignment.individualtrack.domain.Player.CreatePlayerResponse;

public interface CreatePlayerUseCase {
    CreatePlayerResponse createPlayer(CreatePlayerRequest player);
}
