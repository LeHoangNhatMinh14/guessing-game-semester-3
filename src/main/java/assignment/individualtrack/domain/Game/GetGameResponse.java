package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetGameResponse {
    private long id;
    private int score;
    private int time;
    private long playerId;
    private GameStatus status;
}
