package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.SearchThemeUseCase;
import assignment.individualtrack.domain.Themes.SearchThemeRequest;
import assignment.individualtrack.domain.Themes.SearchThemeResponse;
import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchThemeUseCaseImpl implements SearchThemeUseCase {
    private final ThemeRepo themeRepo;

    @Override
    public SearchThemeResponse searchThemes(SearchThemeRequest request) {
        String searchTerm = request.getSearchTerm();
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new SearchThemeResponse(List.of()); // Return empty list in response
        }

        // Fetch and convert themes
        List<ThemeEntity> themeEntities = themeRepo.searchThemesByName(searchTerm);
        List<Theme> themes = themeEntities.stream()
                .map(ThemeConverter::convert) // Convert ThemeEntity to Theme
                .collect(Collectors.toList());

        return new SearchThemeResponse(themes); // Wrap themes in response
    }
}
