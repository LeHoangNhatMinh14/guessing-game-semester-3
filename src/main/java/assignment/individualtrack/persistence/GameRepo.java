package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepo extends JpaRepository<GameEntity, Integer> {
    List<GameEntity> findTop20ByPlayerIdOrderByIdDesc(long playerId);
}
