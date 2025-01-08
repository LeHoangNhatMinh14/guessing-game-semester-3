package assignment.individualtrack.domain.Themes;

import assignment.individualtrack.persistence.entity.WordImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    private Long id;
    private String name;
    private List<WordImage> words;
}
