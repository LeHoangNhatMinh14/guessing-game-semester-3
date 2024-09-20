package assignment.individualtrack.business;

import assignment.individualtrack.domain.CreateThemeRequest;
import assignment.individualtrack.domain.CreateThemeResponse;

public interface CreateThemeUseCase {
    CreateThemeResponse createTheme(CreateThemeRequest themeName);
}
