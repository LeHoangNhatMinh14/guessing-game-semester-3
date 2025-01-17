package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepo extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findTop20ByPlayerIdOrderByIdDesc(long playerId);
}
