package assignment.individualtrack.domain.Player;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerRequest {
    @NonNull
    private String name;
    @NonNull
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
