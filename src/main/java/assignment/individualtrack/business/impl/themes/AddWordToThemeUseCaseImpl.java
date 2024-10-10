package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.AddWordToThemeUseCase;
import assignment.individualtrack.domain.Themes.AddWordThemeResponse;
import assignment.individualtrack.domain.Themes.AddWordToThemeRequest;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddWordToThemeUseCaseImpl implements AddWordToThemeUseCase {

    private final ThemeRepo themeRepo;

    @Override
    public AddWordThemeResponse addwordtoTheme(AddWordToThemeRequest request) {
        Optional<ThemeEntity> themeEntity = savewordtoTheme(request);

        return themeEntity.map(theme ->
                AddWordThemeResponse.builder()
                        .themeId(theme.getId())
                        .build()
        ).orElseGet(() ->
                AddWordThemeResponse.builder()
                        .themeId(null)
                        .build()
        );
    }

    private Optional<ThemeEntity> savewordtoTheme(AddWordToThemeRequest request) {
        Optional<ThemeEntity> themeOptional = themeRepo.findbyID(request.getThemeId());

        // Check if theme exists, return an error if it doesn't
        if (themeOptional.isEmpty()) {
            throw new RuntimeException("Theme with ID " + request.getThemeId() + " does not exist.");
        }

        ThemeEntity theme = themeOptional.get();

        // Initialize words list if it's null
        if (theme.getWords() == null) {
            theme.setWords(new ArrayList<>());
        }

        theme.getWords().add(request.getWord());
        themeRepo.save(theme);
        return Optional.of(theme);
    }
}
