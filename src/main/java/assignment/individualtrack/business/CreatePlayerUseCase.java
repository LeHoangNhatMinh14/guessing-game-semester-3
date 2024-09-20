package assignment.individualtrack.business;

import assignment.individualtrack.domain.CreatePlayerRequest;
import assignment.individualtrack.domain.CreatePlayerResponse;

public interface CreatePlayerUseCase {
    CreatePlayerResponse createPlayer(CreatePlayerRequest player);
}
