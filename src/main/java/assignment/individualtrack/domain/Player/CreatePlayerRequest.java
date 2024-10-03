package assignment.individualtrack.domain.Player;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerRequest {
    @NonNull
    private String name;
    @NonNull
    private String password;
}
