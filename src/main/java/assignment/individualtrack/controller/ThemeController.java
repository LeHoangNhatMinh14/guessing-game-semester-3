package assignment.individualtrack.controller;

import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.exception.WordNotFoundException;
import assignment.individualtrack.business.intefaces.*;
import assignment.individualtrack.domain.Themes.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {

    private final AddWordToThemeUseCase addWordToThemeUseCase;
    private final CreateThemeUseCase addThemeUseCase;
    private final GetAllWordsofThemUseCase getAllWordsofThemUseCase;
    private final GetAllThemeUseCase getAllThemesUseCase;
    private final DeleteThemeUseCase deleteThemeUseCase;
    private final DeleteWordFromThemeUseCase deleteWordFromThemeUseCase;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/words")
    public ResponseEntity<String> addWordToTheme(@RequestBody AddWordToThemeRequest request) {
        String word = request.getWord();
        if (word == null || word.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Word cannot be empty.");
        }
        try {
            addWordToThemeUseCase.addwordtoTheme(request);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Word added to theme.");
    }

    @PreAuthorize("isAuthenticated()")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long themeId) {
        deleteThemeUseCase.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{themeId}/words")
    public ResponseEntity<String> deleteWordFromTheme(@PathVariable Long themeId, @RequestBody String word) {
        if (word == null || word.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Word cannot be empty.");
        }
        try {
            deleteWordFromThemeUseCase.deleteWord(word.trim(), themeId);
        } catch (ThemeNotFoundException | WordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Word deleted from theme.");
    }
}

