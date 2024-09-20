package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.GetAllWordsofThemUseCase;
import assignment.individualtrack.domain.GetAllWordsofThemeRequest;
import assignment.individualtrack.domain.GetAllWordsofThemesResponse;
import assignment.individualtrack.domain.Theme;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllWordsofThemeUseCaseImpl implements GetAllWordsofThemUseCase {
    private final ThemeRepo themeRepo;

    @Override
    public GetAllWordsofThemesResponse getAllWords(GetAllWordsofThemeRequest request) {
        Optional<ThemeEntity> themeOptional = themeRepo.findbyID(request.getThemeId());

        if (themeOptional.isPresent()) {
            ThemeEntity theme = themeOptional.get();

            GetAllWordsofThemesResponse response = new GetAllWordsofThemesResponse();
            response.setThemeId(theme.getId());
            response.setThemeName(theme.getName());
            response.setWords(theme.getWords());

            return response;
        }

        return GetAllWordsofThemesResponse.builder()
                .themeId(request.getThemeId())
                .words(List.of())
                .build();
    }
}
