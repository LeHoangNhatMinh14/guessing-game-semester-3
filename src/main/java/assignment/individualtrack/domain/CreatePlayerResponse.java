package assignment.individualtrack.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePlayerResponse {
    private long playerId;
}
