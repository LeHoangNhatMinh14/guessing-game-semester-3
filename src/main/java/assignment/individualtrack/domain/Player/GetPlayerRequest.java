package assignment.individualtrack.domain.Player;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlayerRequest {
    private long playerId;
}
