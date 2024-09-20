package assignment.individualtrack;

import assignment.individualtrack.business.CreateThemeUseCase;
import assignment.individualtrack.business.impl.CreateThemeUseCaseImpl;
import assignment.individualtrack.domain.CreateThemeRequest;
import assignment.individualtrack.domain.CreateThemeResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateThemeUseCaseTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private CreateThemeUseCase createThemeUseCase; // Use the interface

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createThemeUseCase = new CreateThemeUseCaseImpl(themeRepo); // Inject the implementation
    }

    @Test
    void createTheme_shouldReturnNullIfThemeAlreadyExists() {
        // Arrange
        CreateThemeRequest request = CreateThemeRequest.builder()
                .themeName("Adventure")
                .build();

        when(themeRepo.existsbyName(request.getThemeName())).thenReturn(true);

        // Act
        CreateThemeResponse response = createThemeUseCase.createTheme(request);

        // Assert
        assertNull(response);
        verify(themeRepo, times(1)).existsbyName(request.getThemeName());
        verify(themeRepo, times(0)).save(any(ThemeEntity.class));
    }

    @Test
    void createTheme_shouldSaveAndReturnResponseIfThemeDoesNotExist() {
        // Arrange
        CreateThemeRequest request = CreateThemeRequest.builder()
                .themeName("Adventure")
                .words(List.of("explore", "journey", "quest"))
                .build();

        ThemeEntity savedTheme = ThemeEntity.builder()
                .id(1L)
                .name("Adventure")
                .words(List.of("explore", "journey", "quest"))
                .build();

        when(themeRepo.existsbyName(request.getThemeName())).thenReturn(false);
        when(themeRepo.save(any(ThemeEntity.class))).thenReturn(savedTheme);

        // Act
        CreateThemeResponse response = createThemeUseCase.createTheme(request);

        // Assert
        assertEquals(1L, response.getThemeID());
        verify(themeRepo, times(1)).existsbyName(request.getThemeName());
        verify(themeRepo, times(1)).save(any(ThemeEntity.class));
    }
}
