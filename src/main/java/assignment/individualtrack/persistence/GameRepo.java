package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.GameEntity;

import java.util.Optional;

public interface GameRepo {
    Optional<GameEntity> findbyID(long id);
    GameEntity save (GameEntity game);
}
