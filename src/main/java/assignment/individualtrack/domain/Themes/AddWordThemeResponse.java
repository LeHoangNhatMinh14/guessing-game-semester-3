package assignment.individualtrack.domain.Themes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddWordThemeResponse {
    private Long themeId;
    private String word;
    private String imageUrl; // Add this to return the URL of the uploaded image
}
