package assignment.individualtrack.domain.Game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartGameRequest {
    private Long playerID;
}
