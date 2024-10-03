package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Themes.CreateThemeRequest;
import assignment.individualtrack.domain.Themes.CreateThemeResponse;

public interface CreateThemeUseCase {
    CreateThemeResponse createTheme(CreateThemeRequest themeName);
}
