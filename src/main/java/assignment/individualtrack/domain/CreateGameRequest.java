package assignment.individualtrack.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CreateGameRequest {
    private int id;
    private int score;
    private int time;
    private int playerID;
}
