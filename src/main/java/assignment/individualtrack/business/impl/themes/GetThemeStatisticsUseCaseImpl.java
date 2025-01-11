package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.intefaces.GetThemeStatisticsUseCase;
import assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GetThemeStatisticsUseCaseImpl implements GetThemeStatisticsUseCase {
    private final ThemeRepo themeRepo;

    @Override
    public List<GetThemeStatisticsResponse> execute(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return themeRepo.getThemeStatistics(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching theme statistics.", e);
        }
    }
}
