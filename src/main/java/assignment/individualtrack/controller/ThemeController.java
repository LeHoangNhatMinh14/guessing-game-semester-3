package assignment.individualtrack.controller;

import assignment.individualtrack.business.intefaces.AddWordToThemeUseCase;
import assignment.individualtrack.business.intefaces.CreateThemeUseCase;
import assignment.individualtrack.business.intefaces.GetAllWordsofThemUseCase;
import assignment.individualtrack.domain.Themes.AddWordToThemeRequest;
import assignment.individualtrack.domain.Themes.CreateThemeRequest;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemeRequest;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemesResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
