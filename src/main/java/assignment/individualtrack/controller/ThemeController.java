package assignment.individualtrack.controller;

import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.exception.WordNotFoundException;
import assignment.individualtrack.business.intefaces.*;
import assignment.individualtrack.domain.Themes.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/themes")
@AllArgsConstructor
public class ThemeController {

    private final AddWordToThemeUseCase addWordToThemeUseCase;
    private final CreateThemeUseCase createThemeUseCase;
    private final GetAllWordsofThemUseCase getAllWordsOfThemeUseCase;
    private final GetAllThemeUseCase getAllThemesUseCase;
    private final DeleteThemeUseCase deleteThemeUseCase;
    private final DeleteWordFromThemeUseCase deleteWordFromThemeUseCase;
    private final GetThemeStatisticsUseCase getThemeStatisticsUseCase;
    private final SearchThemeUseCase searchThemeUseCase;
    private static final Logger log = LoggerFactory.getLogger(ThemeController.class);

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/words", consumes = "multipart/form-data")
    public ResponseEntity<String> addWordToTheme(
            @RequestParam("themeId") Long themeId,
            @RequestParam("word") String word,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            byte[] imageBytes = (image != null) ? image.getBytes() : null;

            AddWordToThemeRequest request = AddWordToThemeRequest.builder()
                    .themeId(themeId)
                    .word(word)
                    .image(imageBytes)
                    .build();

            addWordToThemeUseCase.addwordtoTheme(request);
            return ResponseEntity.ok("Word added to theme.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to process image file.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<String> createTheme(@RequestBody CreateThemeRequest request) {
        if (request.getThemeName() == null || request.getThemeName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Theme name cannot be empty.");
        }
        createThemeUseCase.createTheme(request);
        return ResponseEntity.ok("Theme created.");
    }

    @GetMapping("/{themeId}/words")
    public ResponseEntity<GetAllWordsofThemesResponse> getWordsOfTheme(
            @PathVariable Long themeId,
            @RequestParam(required = false) String name) {

        GetAllWordsofThemeRequest request = new GetAllWordsofThemeRequest(themeId, name);
        GetAllWordsofThemesResponse response = getAllWordsOfThemeUseCase.getAllWords(request);

        if (response == null || response.getWords().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GetAllThemesResponse> getAllThemes() {
        GetAllThemesResponse response = getAllThemesUseCase.getAllThemes();
        if (response == null || response.getThemes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
            return ResponseEntity.ok("Word deleted from theme.");
        } catch (ThemeNotFoundException | WordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<GetThemeStatisticsResponse>> getStatistics(
            @RequestParam Optional<Integer> year,
            @RequestParam Optional<Integer> month,
            @RequestParam Optional<Integer> week) {
        try {

            List<GetThemeStatisticsResponse> statistics = getThemeStatisticsUseCase.execute(year, month, week);
            return ResponseEntity.ok(statistics);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<SearchThemeResponse> searchThemes(@RequestParam String term, Authentication authentication) {
        log.debug("Authenticated user: {}", authentication.getName());
        log.debug("User roles: {}", authentication.getAuthorities());

        SearchThemeRequest request = new SearchThemeRequest(term);
        SearchThemeResponse response = searchThemeUseCase.searchThemes(request);

        if (response == null || response.getThemes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }

}
