package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Themes.GetAllWordsofThemeRequest;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemesResponse;

public interface GetAllWordsofThemUseCase {
    GetAllWordsofThemesResponse getAllWords(GetAllWordsofThemeRequest request);
}
