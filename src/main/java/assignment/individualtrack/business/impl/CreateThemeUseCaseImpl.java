package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.CreateThemeUseCase;
import assignment.individualtrack.domain.CreateThemeRequest;
import assignment.individualtrack.domain.CreateThemeResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateThemeUseCaseImpl implements CreateThemeUseCase {
    private final ThemeRepo themeRepo;

    @Override
    public CreateThemeResponse createTheme(CreateThemeRequest themeName) {
        if (themeRepo.existsbyName(themeName.getThemeName())) {
            return null;
        }
        ThemeEntity savedTheme = saveNewTheme(themeName);
        return CreateThemeResponse.builder()
                .themeID(savedTheme.getId())
                .build();
    }
    private ThemeEntity saveNewTheme(CreateThemeRequest request) {
        ThemeEntity newTheme = ThemeEntity.builder()
                .name(request.getThemeName())
                .words(request.getWords())
                .build();


        return themeRepo.save(newTheme);
    }
}
