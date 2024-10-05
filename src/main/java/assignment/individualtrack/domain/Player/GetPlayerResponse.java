package assignment.individualtrack.domain.Player;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlayerResponse {
    private long playerId;
    private String name;
    private int highScore;
}
