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
        // Fetch all themes from the database
        List<ThemeEntity> themeEntities = themeRepo.findAll();

        // Convert database themes to domain objects
        List<Theme> themes = themeEntities.stream()
                .map(ThemeConverter::convert)
                .collect(Collectors.toList());

        // Add words to the "Pokemon" theme dynamically
        themes.forEach(theme -> {
            if ("Pokemon".equalsIgnoreCase(theme.getName())) {
                List<WordImage> pokemonData = pokemonService.getPokemonData(100); // Fetch Pokémon data dynamically
                theme.setWords(pokemonData); // Set the dynamic words to the theme
            }
        });

        return new GetAllThemesResponse(themes);
    }
}
