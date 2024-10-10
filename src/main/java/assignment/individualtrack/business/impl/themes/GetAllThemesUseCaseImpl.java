package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.GetAllThemeUseCase;
import assignment.individualtrack.domain.Themes.GetAllThemesResponse;
import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllThemesUseCaseImpl implements GetAllThemeUseCase {
    private final ThemeRepo themeRepo;

    @Override
    public GetAllThemesResponse getAllThemes() {
        List<ThemeEntity> themeEntities = themeRepo.findAll();

        // Convert the list of ThemeEntity to a list of Theme using the converter
        List<Theme> themes = themeEntities.stream()
                .map(ThemeConverter::convert)
                .collect(Collectors.toList());

        return new GetAllThemesResponse(themes);
    }
}
