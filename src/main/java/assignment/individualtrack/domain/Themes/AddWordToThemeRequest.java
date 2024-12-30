package assignment.individualtrack.domain.Themes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddWordToThemeRequest {
    private Long themeId;
    private String word;
    private byte[] image; // Add this to receive the image file as bytes
}
