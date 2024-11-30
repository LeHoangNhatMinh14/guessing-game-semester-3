package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.exception.WordNotFoundException;
import assignment.individualtrack.business.intefaces.DeleteWordFromThemeUseCase;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteWordFromThemeUseCaseImpl implements DeleteWordFromThemeUseCase {

    private final ThemeRepo themeRepo;

    @Override
    public void deleteWord(String word, Long id) {
        // Validate theme ID
        if (id == null) {
            throw new ThemeNotFoundException("Theme ID cannot be null.");
        }

        // Check if theme exists
        if (!themeRepo.existsById(id)) {
            throw new ThemeNotFoundException("Theme not found with ID: " + id);
        }

        // Validate word
        if (word == null || word.trim().isEmpty()) {
            throw new WordNotFoundException("Word cannot be null or empty.");
        }

        // Check if the word exists in the theme
        if (!themeRepo.existsWordInTheme(id, word)) {
            throw new WordNotFoundException("Word not found in the theme.");
        }

        // Fetch the theme entity
        ThemeEntity themeEntity = themeRepo.findById(id)
                .orElseThrow(() -> new ThemeNotFoundException("Theme not found with ID: " + id));

        // Remove the word from the theme's word list
        themeEntity.getWords().remove(word);

        // Save the updated theme entity back to the repository
        themeRepo.save(themeEntity);
    }
}
