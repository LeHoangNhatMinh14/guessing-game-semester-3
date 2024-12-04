package assignment.individualtrack.domain.Game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartGameResponse {
    private long gameId;
    private long playerId;
}
