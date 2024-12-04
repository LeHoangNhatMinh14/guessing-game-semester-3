package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndGameRequest {
    private long gameId;
    private int score;
    private int time;
    private GameStatus status;
    private int correctGuesses;
    private int incorrectGuesses;
}
