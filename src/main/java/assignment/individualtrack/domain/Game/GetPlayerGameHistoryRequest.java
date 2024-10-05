package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlayerGameHistoryRequest {
    private long playerId;
}
