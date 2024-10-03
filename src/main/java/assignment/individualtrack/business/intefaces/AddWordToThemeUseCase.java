package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Themes.AddWordThemeResponse;
import assignment.individualtrack.domain.Themes.AddWordToThemeRequest;

public interface AddWordToThemeUseCase {
    AddWordThemeResponse addwordtoTheme (AddWordToThemeRequest request);
}
