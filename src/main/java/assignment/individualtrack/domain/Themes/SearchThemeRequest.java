package assignment.individualtrack.domain.Themes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchThemeRequest {
    @NotBlank(message = "Search term must not be empty")
    private String searchTerm;
}
