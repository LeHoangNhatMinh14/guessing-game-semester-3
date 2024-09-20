package assignment.individualtrack.business;

import assignment.individualtrack.domain.GetAllWordsofThemeRequest;
import assignment.individualtrack.domain.GetAllWordsofThemesResponse;

public interface GetAllWordsofThemUseCase {
    GetAllWordsofThemesResponse getAllWords(GetAllWordsofThemeRequest request);
}
