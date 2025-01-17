package assignment.individualtrack.theme;

import assignment.individualtrack.business.exception.ThemeStatisticsFetchException;
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
import java.util.Optional;

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
    void execute_withMonthAndYearFilter_shouldReturnStatistics() {
        // Arrange
        LocalDateTime expectedStartDate = LocalDateTime.of(2025, 1, 1, 0, 0).withNano(0);
        LocalDateTime expectedEndDate = expectedStartDate.plusMonths(1).minusSeconds(1);

        List<GetThemeStatisticsResponse> mockResponse = List.of(
                GetThemeStatisticsResponse.builder()
                        .themeName("Space")
                        .totalPlays(25)
                        .build()
        );

        when(themeRepo.getThemeStatistics(expectedStartDate, expectedEndDate)).thenReturn(mockResponse);

        // Act
        List<GetThemeStatisticsResponse> result = getThemeStatisticsUseCase.execute(Optional.of(2025), Optional.of(1), Optional.empty());

        // Assert
        assertNotNull(result, "Result should not be null.");
        assertEquals(1, result.size(), "Expected exactly one result.");
        assertEquals("Space", result.get(0).getThemeName(), "Theme name mismatch.");
        assertEquals(25, result.get(0).getTotalPlays(), "Total plays mismatch.");

        verify(themeRepo, times(1)).getThemeStatistics(expectedStartDate, expectedEndDate);
    }

    @Test
    void execute_withYearOnlyFilter_shouldReturnStatistics() {
        // Arrange
        LocalDateTime expectedStartDate = LocalDateTime.of(2025, 1, 1, 0, 0).withNano(0);
        LocalDateTime expectedEndDate = expectedStartDate.plusYears(1).minusSeconds(1);

        List<GetThemeStatisticsResponse> mockResponse = List.of(
                GetThemeStatisticsResponse.builder()
                        .themeName("Adventure")
                        .totalPlays(50)
                        .build()
        );

        when(themeRepo.getThemeStatistics(expectedStartDate, expectedEndDate)).thenReturn(mockResponse);

        // Act
        List<GetThemeStatisticsResponse> result = getThemeStatisticsUseCase.execute(Optional.of(2025), Optional.empty(), Optional.empty());

        // Assert
        assertNotNull(result, "Result should not be null.");
        assertEquals(1, result.size(), "Expected exactly one result.");
        assertEquals("Adventure", result.get(0).getThemeName(), "Theme name mismatch.");
        assertEquals(50, result.get(0).getTotalPlays(), "Total plays mismatch.");

        verify(themeRepo, times(1)).getThemeStatistics(expectedStartDate, expectedEndDate);
    }

    @Test
    void execute_withNoFilter_shouldReturnEmptyList() {
        // Arrange
        LocalDateTime now = LocalDateTime.now().withNano(0);
        LocalDateTime expectedStartDate = now.minusMonths(1);
        LocalDateTime expectedEndDate = now;

        // Mock themeRepo to return an empty list
        when(themeRepo.getThemeStatistics(argThat(start -> start.isEqual(expectedStartDate) || start.isBefore(expectedStartDate)),
                argThat(end -> end.isEqual(expectedEndDate) || end.isAfter(expectedEndDate))))
                .thenReturn(List.of());

        // Act
        List<GetThemeStatisticsResponse> result = getThemeStatisticsUseCase.execute(Optional.empty(), Optional.empty(), Optional.empty());

        // Assert
        assertNotNull(result, "Result should not be null.");
        assertTrue(result.isEmpty(), "Expected an empty result list.");

        // Verify that themeRepo was called once
        verify(themeRepo, times(1)).getThemeStatistics(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void execute_repoThrowsException_shouldThrowThemeStatisticsFetchException() {
        // Arrange
        when(themeRepo.getThemeStatistics(any(), any()))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        ThemeStatisticsFetchException exception = assertThrows(
                ThemeStatisticsFetchException.class, this::invokeGetThemeStatistics);

        // Assert the cause of the exception
        assertTrue(exception.getCause() instanceof RuntimeException, "Expected cause to be RuntimeException");
        assertEquals("Database error", exception.getCause().getMessage(), "Expected specific cause message");

        verify(themeRepo, times(1)).getThemeStatistics(any(), any());
    }

    // Helper method to encapsulate the invocation logic
    private void invokeGetThemeStatistics() {
        getThemeStatisticsUseCase.execute(Optional.empty(), Optional.empty(), Optional.empty());
    }
}
