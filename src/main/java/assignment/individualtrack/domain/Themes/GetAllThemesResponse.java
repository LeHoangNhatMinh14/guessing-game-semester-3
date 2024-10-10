package assignment.individualtrack.domain.Themes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllThemesResponse {
    private List<Theme> themes;
}
