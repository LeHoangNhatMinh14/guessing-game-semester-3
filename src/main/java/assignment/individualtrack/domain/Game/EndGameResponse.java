package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndGameResponse {
    private int finalScore;
    private int timeTaken;
    private int correctGuesses;
    private int incorrectGuesses;
    private String message;
}
