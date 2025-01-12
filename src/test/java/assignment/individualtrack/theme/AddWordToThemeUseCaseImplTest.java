package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.AddWordToThemeUseCaseImpl;
import assignment.individualtrack.domain.Themes.AddWordThemeResponse;
import assignment.individualtrack.domain.Themes.AddWordToThemeRequest;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddWordToThemeUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private AddWordToThemeUseCaseImpl addWordToThemeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addWordToTheme_successful() {
        // Arrange
        AddWordToThemeRequest request = AddWordToThemeRequest.builder()
                .themeId(1L)
                .word("NewWord")
                .build();
        ThemeEntity theme = ThemeEntity.builder()
                .id(1L)
                .words(new ArrayList<>())
                .build();

        when(themeRepo.findById(request.getThemeId())).thenReturn(Optional.of(theme));
        when(themeRepo.save(any(ThemeEntity.class))).thenReturn(theme);

        // Act
        AddWordThemeResponse response = addWordToThemeUseCase.addwordtoTheme(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getThemeId());
        verify(themeRepo, times(1)).findById(request.getThemeId());
        verify(themeRepo, times(1)).save(theme);
    }

    @Test
    void addWordToTheme_themeNotFound() {
        // Arrange
        AddWordToThemeRequest request = AddWordToThemeRequest.builder()
                .themeId(999L)
                .word("NewWord")
                .build();
        when(themeRepo.findById(request.getThemeId())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> addWordToThemeUseCase.addwordtoTheme(request));
        assertEquals("Theme with ID 999 does not exist.", exception.getMessage());
        verify(themeRepo, times(1)).findById(request.getThemeId());
        verify(themeRepo, never()).save(any(ThemeEntity.class));
    }

    @Test
    void addWordToTheme_initializeWordsListIfNull() {
        // Arrange
        AddWordToThemeRequest request = AddWordToThemeRequest.builder()
                .themeId(1L)
                .word("NewWord")
                .build();

        ThemeEntity theme = ThemeEntity.builder()
                .id(1L)
                .words(null) // Simulate null words list
                .build();

        when(themeRepo.findById(request.getThemeId())).thenReturn(Optional.of(theme));
        when(themeRepo.save(any(ThemeEntity.class))).thenReturn(theme);

        // Act
        AddWordThemeResponse response = addWordToThemeUseCase.addwordtoTheme(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getThemeId());
        assertNotNull(theme.getWords()); // Verify the list is initialized

        // Verify the word is added (as a WordImage object)
        assertTrue(theme.getWords().stream()
                .anyMatch(wordImage -> "NewWord".equals(wordImage.getWord())));

        verify(themeRepo, times(1)).findById(request.getThemeId());
        verify(themeRepo, times(1)).save(theme);
    }
}
