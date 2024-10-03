package assignment.individualtrack.domain.Themes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllWordsofThemesResponse {
    private Long themeId;
    private String themeName;
    private List<String> words;
}
