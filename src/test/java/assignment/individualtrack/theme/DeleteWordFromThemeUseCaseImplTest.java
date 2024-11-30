package assignment.individualtrack.theme;

import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.exception.WordNotFoundException;
import assignment.individualtrack.business.impl.themes.DeleteWordFromThemeUseCaseImpl;
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

class DeleteWordFromThemeUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private DeleteWordFromThemeUseCaseImpl deleteWordFromThemeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteWord_successful() {
        // Arrange
        Long themeId = 1L;
        String wordToDelete = "exampleWord";
        ThemeEntity themeEntity = ThemeEntity.builder()
                .id(themeId)
                .words(new ArrayList<>(java.util.List.of(wordToDelete, "anotherWord")))
                .build();

        when(themeRepo.existsById(themeId)).thenReturn(true);
        when(themeRepo.existsWordInTheme(themeId, wordToDelete)).thenReturn(true);
        when(themeRepo.findById(themeId)).thenReturn(Optional.of(themeEntity));

        // Act
        deleteWordFromThemeUseCase.deleteWord(wordToDelete, themeId);

        // Assert
        verify(themeRepo, times(1)).existsById(themeId);
        verify(themeRepo, times(1)).existsWordInTheme(themeId, wordToDelete);
        verify(themeRepo, times(1)).findById(themeId);
        verify(themeRepo, times(1)).save(themeEntity);
        assertFalse(themeEntity.getWords().contains(wordToDelete));
    }

    @Test
    void deleteWord_themeNotFound() {
        // Arrange
        Long themeId = 999L;
        String wordToDelete = "exampleWord";

        when(themeRepo.existsById(themeId)).thenReturn(false);

        // Act & Assert
        ThemeNotFoundException exception = assertThrows(ThemeNotFoundException.class, () -> deleteWordFromThemeUseCase.deleteWord(wordToDelete, themeId));
        assertEquals("Theme not found with ID: 999", exception.getMessage());
        verify(themeRepo, times(1)).existsById(themeId);
        verify(themeRepo, never()).existsWordInTheme(any(), any());
        verify(themeRepo, never()).findById(any());
        verify(themeRepo, never()).save(any());
    }

    @Test
    void deleteWord_wordNotFound() {
        // Arrange
        Long themeId = 1L;
        String wordToDelete = "nonExistingWord";

        when(themeRepo.existsById(themeId)).thenReturn(true);
        when(themeRepo.existsWordInTheme(themeId, wordToDelete)).thenReturn(false);

        // Act & Assert
        WordNotFoundException exception = assertThrows(WordNotFoundException.class, () -> deleteWordFromThemeUseCase.deleteWord(wordToDelete, themeId));
        assertEquals("Word not found in the theme.", exception.getMessage());
        verify(themeRepo, times(1)).existsById(themeId);
        verify(themeRepo, times(1)).existsWordInTheme(themeId, wordToDelete);
        verify(themeRepo, never()).findById(any());
        verify(themeRepo, never()).save(any());
    }

    @Test
    void deleteWord_nullThemeId() {
        // Arrange
        String wordToDelete = "exampleWord";

        // Act & Assert
        ThemeNotFoundException exception = assertThrows(ThemeNotFoundException.class, () -> deleteWordFromThemeUseCase.deleteWord(wordToDelete, null));
        assertEquals("Theme ID cannot be null.", exception.getMessage());
        verify(themeRepo, never()).existsById(any());
        verify(themeRepo, never()).existsWordInTheme(any(), any());
        verify(themeRepo, never()).findById(any());
        verify(themeRepo, never()).save(any());
    }
}

