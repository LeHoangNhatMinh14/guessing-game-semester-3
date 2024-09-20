package assignment.individualtrack.controller;

import assignment.individualtrack.business.AddWordToThemeUseCase;
import assignment.individualtrack.business.CreateGameUseCase;
import assignment.individualtrack.business.CreateThemeUseCase;
import assignment.individualtrack.business.GetAllWordsofThemUseCase;
import assignment.individualtrack.domain.*;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {

    private final AddWordToThemeUseCase addWordToThemeUseCase;
    private final CreateThemeUseCase addThemeUseCase;
    private final GetAllWordsofThemUseCase getAllWordsofThemUseCase;

    @PostMapping("/{themeId}/words")
    public ResponseEntity<String> addWordToTheme(@PathVariable Long themeId, @RequestBody AddWordToThemeRequest request) {
        String word = request.getWord();
        if (word == null || word.isEmpty()) {
            return ResponseEntity.badRequest().body("Word cannot be empty");
        }

        addWordToThemeUseCase.addwordtoTheme(request);

        return ResponseEntity.ok("Word added to theme");
    }

    @PostMapping
    public ResponseEntity<String> addTheme(@RequestBody CreateThemeRequest request) {
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
}
