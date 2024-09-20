package assignment.individualtrack.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddWordThemeResponse {
    private Long themeId;
    private String word;
}
