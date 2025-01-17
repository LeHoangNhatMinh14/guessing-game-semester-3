package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse;

import java.util.List;
import java.util.Optional;

public interface GetThemeStatisticsUseCase {
    List<GetThemeStatisticsResponse> execute(Optional<Integer> year, Optional<Integer> month, Optional<Integer> week);
}
