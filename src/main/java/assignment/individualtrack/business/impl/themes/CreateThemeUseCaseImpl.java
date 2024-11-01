package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.CreateThemeUseCase;
import assignment.individualtrack.domain.Themes.CreateThemeRequest;
import assignment.individualtrack.domain.Themes.CreateThemeResponse;
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
        if (themeRepo.existsByName(themeName.getThemeName())) {
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
