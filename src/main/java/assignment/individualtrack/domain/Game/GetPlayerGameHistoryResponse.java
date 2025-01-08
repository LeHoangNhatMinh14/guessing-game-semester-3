package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetPlayerGameHistoryResponse {
    private List<GameResponse> games;

    @Data
    @Builder
    public static class GameResponse {
        private long gameId;
        private int score;
        private int time;
        private GameStatus status;
        private int correctGuesses;
        private int wrongGuesses;
        private String themeName; // Added to include theme details
        private LocalDateTime playedAt; // Added to include the time the game was played
    }
}

