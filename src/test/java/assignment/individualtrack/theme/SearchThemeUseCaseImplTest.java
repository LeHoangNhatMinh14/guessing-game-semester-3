package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.SearchThemeUseCaseImpl;
import assignment.individualtrack.domain.Themes.SearchThemeRequest;
import assignment.individualtrack.domain.Themes.SearchThemeResponse;
import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchThemeUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private SearchThemeUseCaseImpl searchThemeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchThemes_emptySearchTerm_shouldReturnEmptyResponse() {
        // Arrange
        SearchThemeRequest request = new SearchThemeRequest("");

        // Act
        SearchThemeResponse response = searchThemeUseCase.searchThemes(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getThemes().isEmpty(), "Expected themes list to be empty");
        verifyNoInteractions(themeRepo);
    }

    @Test
    void searchThemes_nullSearchTerm_shouldReturnEmptyResponse() {
        // Arrange
        SearchThemeRequest request = new SearchThemeRequest(null);

        // Act
        SearchThemeResponse response = searchThemeUseCase.searchThemes(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getThemes().isEmpty(), "Expected themes list to be empty");
        verifyNoInteractions(themeRepo);
    }

    @Test
    void searchThemes_validSearchTerm_shouldReturnMatchingThemes() {
        // Arrange
        String searchTerm = "Nature";
        SearchThemeRequest request = new SearchThemeRequest(searchTerm);

        ThemeEntity themeEntity1 = ThemeEntity.builder()
                .id(1L)
                .name("Nature Theme 1")
                .build();

        ThemeEntity themeEntity2 = ThemeEntity.builder()
                .id(2L)
                .name("Nature Theme 2")
                .build();

        List<ThemeEntity> mockEntities = List.of(themeEntity1, themeEntity2);

        when(themeRepo.searchThemesByName(searchTerm)).thenReturn(mockEntities);

        // Act
        SearchThemeResponse response = searchThemeUseCase.searchThemes(request);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getThemes().size(), "Expected 2 themes to match");
        assertEquals("Nature Theme 1", response.getThemes().get(0).getName());
        assertEquals("Nature Theme 2", response.getThemes().get(1).getName());

        verify(themeRepo, times(1)).searchThemesByName(searchTerm);
    }

    @Test
    void searchThemes_noMatchingThemes_shouldReturnEmptyResponse() {
        // Arrange
        String searchTerm = "NonExisting";
        SearchThemeRequest request = new SearchThemeRequest(searchTerm);

        when(themeRepo.searchThemesByName(searchTerm)).thenReturn(List.of());

        // Act
        SearchThemeResponse response = searchThemeUseCase.searchThemes(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.getThemes().isEmpty(), "Expected no themes to match");

        verify(themeRepo, times(1)).searchThemesByName(searchTerm);
    }
}


