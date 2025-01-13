package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.GetThemeStatisticsUseCaseImpl;
import assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetThemeStatisticsUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private GetThemeStatisticsUseCaseImpl getThemeStatisticsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_validDates_shouldReturnStatistics() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 31, 23, 59);

        List<GetThemeStatisticsResponse> mockResponse = List.of(
                GetThemeStatisticsResponse.builder()
                        .themeName("Nature")
                        .totalPlays(10)
                        .build(),
                GetThemeStatisticsResponse.builder()
                        .themeName("Space")
                        .totalPlays(5)
                        .build()
        );

        when(themeRepo.getThemeStatistics(startDate, endDate)).thenReturn(mockResponse);

        // Act
        List<GetThemeStatisticsResponse> result = getThemeStatisticsUseCase.execute(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Nature", result.get(0).getThemeName());
        assertEquals(10, result.get(0).getTotalPlays());
        assertEquals("Space", result.get(1).getThemeName());
        assertEquals(5, result.get(1).getTotalPlays());

        verify(themeRepo, times(1)).getThemeStatistics(startDate, endDate);
    }

    @Test
    void execute_repoThrowsException_shouldThrowRuntimeException() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 31, 23, 59);

        when(themeRepo.getThemeStatistics(startDate, endDate)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                getThemeStatisticsUseCase.execute(startDate, endDate)
        );
        assertEquals("An error occurred while fetching theme statistics.", exception.getMessage());
        verify(themeRepo, times(1)).getThemeStatistics(startDate, endDate);
    }
}


