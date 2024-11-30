package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.GetAllThemesUseCaseImpl;
import assignment.individualtrack.domain.Themes.GetAllThemesResponse;
import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllThemesUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private GetAllThemesUseCaseImpl getAllThemesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllThemes_successful() {
        // Arrange
        ThemeEntity theme1 = ThemeEntity.builder().id(1L).name("Theme1").build();
        ThemeEntity theme2 = ThemeEntity.builder().id(2L).name("Theme2").build();
        when(themeRepo.findAll()).thenReturn(List.of(theme1, theme2));

        // Act
        GetAllThemesResponse response = getAllThemesUseCase.getAllThemes();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getThemes().size());
        List<String> themeNames = response.getThemes().stream().map(Theme::getName).collect(Collectors.toList());
        assertTrue(themeNames.contains("Theme1"));
        assertTrue(themeNames.contains("Theme2"));
        verify(themeRepo, times(1)).findAll();
    }

    @Test
    void getAllThemes_emptyList() {
        // Arrange
        when(themeRepo.findAll()).thenReturn(List.of());

        // Act
        GetAllThemesResponse response = getAllThemesUseCase.getAllThemes();

        // Assert
        assertNotNull(response);
        assertTrue(response.getThemes().isEmpty());
        verify(themeRepo, times(1)).findAll();
    }
}

