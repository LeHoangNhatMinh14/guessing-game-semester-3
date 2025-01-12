package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.exception.WordValidationException;
import assignment.individualtrack.business.impl.ImgurService;
import assignment.individualtrack.business.intefaces.AddWordToThemeUseCase;
import assignment.individualtrack.domain.Themes.AddWordThemeResponse;
import assignment.individualtrack.domain.Themes.AddWordToThemeRequest;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.entity.WordImage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddWordToThemeUseCaseImpl implements AddWordToThemeUseCase {

    private final ThemeRepo themeRepo;
    private final ImgurService imgurService;

    @Override
    public AddWordThemeResponse addwordtoTheme(AddWordToThemeRequest request) {
        Optional<ThemeEntity> themeOptional = themeRepo.findById(request.getThemeId());

        if (themeOptional.isEmpty()) {
            throw new ThemeNotFoundException("Theme with ID " + request.getThemeId() + " does not exist.");
        }

        ThemeEntity theme = themeOptional.get();

        // Initialize words list if null
        if (theme.getWords() == null) {
            theme.setWords(new ArrayList<>());
        }

        // Validate the word
        String newWord = request.getWord();
        if (newWord == null || !newWord.matches("^[a-zA-Z]+$")) {
            throw new WordValidationException("The word must contain only alphabetic characters.");
        }

        // Check for case-insensitive uniqueness
        boolean wordExists = theme.getWords().stream()
                .anyMatch(existingWord -> existingWord.getWord().equalsIgnoreCase(newWord));
        if (wordExists) {
            throw new WordValidationException("The word '" + newWord + "' already exists in the theme.");
        }

        // Upload the image to Imgur and get the URL
        String imageUrl = null;
        if (request.getImage() != null && request.getImage().length > 0) {
            try {
                imageUrl = imgurService.uploadImage(request.getImage()); // Imgur upload
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image to Imgur", e);
            }
        }

        if (theme.getWords() == null) {
            theme.setWords(new ArrayList<>());
        }

        // Save the word and image URL
        WordImage newWordImage = new WordImage(newWord, imageUrl);
        theme.getWords().add(newWordImage);

        themeRepo.save(theme);

        return AddWordThemeResponse.builder()
                .themeId(theme.getId())
                .word(newWord)
                .imageUrl(imageUrl) // Return the uploaded image URL
                .build();
    }
}



