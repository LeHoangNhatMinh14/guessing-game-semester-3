package assignment.individualtrack.persistence.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerEntity {
    private Long id;
    private String name;
    private Integer highscore;
    private String password;
}
