package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.GetAllWordsofThemUseCase;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemeRequest;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemesResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.entity.WordImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAllWordsofThemeUseCaseImpl implements GetAllWordsofThemUseCase {
    private final ThemeRepo themeRepo;
    private final PokemonService pokemonService;

    @Override
    public GetAllWordsofThemesResponse getAllWords(GetAllWordsofThemeRequest request) {
        // Handle "Pokemon" theme dynamically
        if ("Pokemon".equalsIgnoreCase(request.getThemeName())) {
            List<WordImage> pokemonData = pokemonService.getPokemonData(10); // Fetch data from the API
            return GetAllWordsofThemesResponse.builder()
                    .themeId(null) // No database ID for dynamic themes
                    .themeName("Pokemon")
                    .words(pokemonData)
                    .build();
        }

        // Fallback to database-based theme retrieval
        Optional<ThemeEntity> themeOptional = themeRepo.findById(request.getThemeId());
        if (themeOptional.isPresent()) {
            ThemeEntity theme = themeOptional.get();
            return GetAllWordsofThemesResponse.builder()
                    .themeId(theme.getId())
                    .themeName(theme.getName())
                    .words(theme.getWords())
                    .build();
        }

        // Handle non-existent theme
        return GetAllWordsofThemesResponse.builder()
                .themeId(request.getThemeId())
                .themeName(request.getThemeName())
                .words(List.of())
                .build();
    }

}
