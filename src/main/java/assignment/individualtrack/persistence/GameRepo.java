package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameRepo {
    Optional<GameEntity> findbyID(long id);
    GameEntity save (GameEntity game);
    List<GameEntity> findTop20ByPlayerIdOrderByIdDesc(long playerId);
}
