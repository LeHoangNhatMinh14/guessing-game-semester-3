package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface GetThemeStatisticsUseCase {
    List<GetThemeStatisticsResponse> execute(LocalDateTime startDate, LocalDateTime endDate);
}
