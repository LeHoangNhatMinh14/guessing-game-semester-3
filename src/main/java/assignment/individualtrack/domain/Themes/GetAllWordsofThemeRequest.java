package assignment.individualtrack.domain.Themes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllWordsofThemeRequest {
    private Long themeId;
}
