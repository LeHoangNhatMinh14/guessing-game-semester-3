package assignment.individualtrack.business;

import assignment.individualtrack.domain.AddWordThemeResponse;
import assignment.individualtrack.domain.AddWordToThemeRequest;

public interface AddWordToThemeUseCase {
    AddWordThemeResponse addwordtoTheme (AddWordToThemeRequest request);
}
