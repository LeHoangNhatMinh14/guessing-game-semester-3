package assignment.individualtrack.domain.Themes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchThemeResponse {
    List<Theme> themes;
}
