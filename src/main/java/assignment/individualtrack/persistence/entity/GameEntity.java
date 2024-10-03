package assignment.individualtrack.persistence.entity;

import assignment.individualtrack.domain.Game.GameStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GameEntity {
    private long id;
    private int score;
    private int time;
    private long playerId;
    private GameStatus status;
}
