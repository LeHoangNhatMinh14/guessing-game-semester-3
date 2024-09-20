package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.AddWordToThemeUseCase;
import assignment.individualtrack.domain.AddWordThemeResponse;
import assignment.individualtrack.domain.AddWordToThemeRequest;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;

import javax.print.attribute.standard.MediaSize;
import javax.swing.text.html.Option;
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

    private Optional<ThemeEntity> savewordtoTheme (AddWordToThemeRequest request) {
        Optional<ThemeEntity> themeOptional = themeRepo.findbyID(request.getThemeId());
        if (themeOptional.isPresent()) {
            ThemeEntity theme = themeOptional.get();
            theme.getWords().add(request.getWord());
            themeRepo.save(theme);
            return Optional.of(theme);
        }
        return Optional.empty();
    }
}
