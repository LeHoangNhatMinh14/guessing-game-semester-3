package assignment.individualtrack.domain.Player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditPlayerRequest {
    private Long id;
    private String name;
    private int highscore;
    private String password;
}
