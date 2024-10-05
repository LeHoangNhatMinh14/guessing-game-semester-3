package assignment.individualtrack.domain.Game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private long id;
    private int score;
    private int time;
    private long playerId;
    private GameStatus status;
}
