package assignment.individualtrack.theme;

import assignment.individualtrack.business.exception.PlayerNotFoundException;
import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.impl.themes.DeleteThemeUseCaseImpl;
import assignment.individualtrack.persistence.ThemeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteThemeUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private DeleteThemeUseCaseImpl deleteThemeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteTheme_successful() {
        // Arrange
        Long themeId = 1L;
        when(themeRepo.existsById(themeId)).thenReturn(true);

        // Act
        deleteThemeUseCase.deleteTheme(themeId);

        // Assert
        verify(themeRepo, times(1)).existsById(themeId);
        verify(themeRepo, times(1)).deleteById(themeId);
    }

    @Test
    void deleteTheme_themeNotFound() {
        // Arrange
        Long themeId = 999L;
        when(themeRepo.existsById(themeId)).thenReturn(false);

        // Act & Assert
        PlayerNotFoundException exception = assertThrows(PlayerNotFoundException.class, () -> deleteThemeUseCase.deleteTheme(themeId));
        assertEquals("Player with ID 999 does not exist", exception.getMessage());
        verify(themeRepo, times(1)).existsById(themeId);
        verify(themeRepo, never()).deleteById(themeId);
    }

    @Test
    void deleteTheme_nullThemeId() {
        // Act & Assert
        ThemeNotFoundException exception = assertThrows(ThemeNotFoundException.class, () -> deleteThemeUseCase.deleteTheme(null));
        assertEquals("Theme ID cannot be null", exception.getMessage());
        verify(themeRepo, never()).existsById(any());
        verify(themeRepo, never()).deleteById(any());
    }
}

