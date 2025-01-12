package assignment.individualtrack.domain.Themes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GetAllThemesResponse {
    private List<Theme> themes;
}
