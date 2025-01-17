package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Themes.SearchThemeRequest;
import assignment.individualtrack.domain.Themes.SearchThemeResponse;

public interface SearchThemeUseCase {
    SearchThemeResponse searchThemes(SearchThemeRequest request);
}
