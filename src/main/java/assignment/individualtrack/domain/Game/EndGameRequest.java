package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndGameRequest {
    private int gameId;
    private int score;
    private int time;
    private int correctGuesses;
    private int incorrectGuesses;
}
