package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

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
    }
}
