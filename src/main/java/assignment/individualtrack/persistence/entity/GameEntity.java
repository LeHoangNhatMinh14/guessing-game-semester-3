package assignment.individualtrack.persistence.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GameEntity {
    private long id;
    private int score;
    private int time;
    private PlayerEntity player;
}
