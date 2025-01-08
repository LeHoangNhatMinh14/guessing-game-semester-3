package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.GetAllThemeUseCase;
import assignment.individualtrack.domain.Themes.GetAllThemesResponse;
import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.entity.WordImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllThemesUseCaseImpl implements GetAllThemeUseCase {
    private final ThemeRepo themeRepo;
    private final PokemonService pokemonService;

    @Override
    public GetAllThemesResponse getAllThemes() {
        List<ThemeEntity> themeEntities = themeRepo.findAll();

        // Convert database themes to domain objects
        List<Theme> themes = themeEntities.stream()
                .map(ThemeConverter::convert)
                .collect(Collectors.toList());

        // Add the dynamic "Pokemon" theme
        List<WordImage> pokemonData = pokemonService.getPokemonData(100);
        themes.add(new Theme(0L, "Pokemon",pokemonData)); // ID is null for dynamic themes

        return new GetAllThemesResponse(themes);
    }
}
