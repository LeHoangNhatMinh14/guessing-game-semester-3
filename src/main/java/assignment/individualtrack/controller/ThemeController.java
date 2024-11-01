package assignment.individualtrack.controller;

import assignment.individualtrack.business.intefaces.AddWordToThemeUseCase;
import assignment.individualtrack.business.intefaces.CreateThemeUseCase;
import assignment.individualtrack.business.intefaces.GetAllThemeUseCase;
import assignment.individualtrack.business.intefaces.GetAllWordsofThemUseCase;
import assignment.individualtrack.domain.Themes.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ThemeController {

    private final AddWordToThemeUseCase addWordToThemeUseCase;
    private final CreateThemeUseCase addThemeUseCase;
    private final GetAllWordsofThemUseCase getAllWordsofThemUseCase;
    private final GetAllThemeUseCase getAllThemesUseCase;

    @PostMapping("/words")
    public ResponseEntity<String> addWordToTheme(@RequestBody AddWordToThemeRequest request) {
        // Validate that the word is not null or empty
        String word = request.getWord();
        if (word == null || word.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Word cannot be empty.");
        }

        // Check if the theme exists
        try {
            addWordToThemeUseCase.addwordtoTheme(request);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok("Word added to theme.");
    }

    @PostMapping
    public ResponseEntity<String> createTheme(@RequestBody CreateThemeRequest request) {
        String themeName = request.getThemeName();
        if (themeName == null || themeName.isEmpty()) {
            return ResponseEntity.badRequest().body("Theme name cannot be empty");
        }

        addThemeUseCase.createTheme(request);

        return ResponseEntity.ok("Theme created");
    }

    @GetMapping("/{themeId}/words")
    public ResponseEntity<GetAllWordsofThemesResponse> getThemes(@PathVariable Long themeId) {
        GetAllWordsofThemeRequest request = new GetAllWordsofThemeRequest(themeId);

        GetAllWordsofThemesResponse response = getAllWordsofThemUseCase.getAllWords(request);

        if (response == null || response.getWords().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GetAllThemesResponse> getAllThemes() {
        GetAllThemesResponse response = getAllThemesUseCase.getAllThemes();
        if (response == null || response.getThemes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(response);
    }

}
