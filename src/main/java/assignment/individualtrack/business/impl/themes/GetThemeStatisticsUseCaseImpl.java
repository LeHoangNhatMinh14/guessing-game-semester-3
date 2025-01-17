package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.exception.InvalidStatisticsRequestException;
import assignment.individualtrack.business.exception.ThemeStatisticsFetchException;
import assignment.individualtrack.business.intefaces.GetThemeStatisticsUseCase;
import assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetThemeStatisticsUseCaseImpl implements GetThemeStatisticsUseCase {
    private final ThemeRepo themeRepo;

    @Override
    public List<GetThemeStatisticsResponse> execute(Optional<Integer> year, Optional<Integer> month, Optional<Integer> week) {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startDate;
            LocalDateTime endDate = now;

            if (week.isPresent()) {
                if (week.get() <= 0) {
                    throw new InvalidStatisticsRequestException("Week parameter must be greater than 0.");
                }
                startDate = now.minusWeeks(week.get());
            } else if (month.isPresent() && year.isPresent()) {
                if (month.get() < 1 || month.get() > 12) {
                    throw new InvalidStatisticsRequestException("Month parameter must be between 1 and 12.");
                }
                startDate = LocalDateTime.of(year.get(), month.get(), 1, 0, 0);
                endDate = startDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).minusSeconds(1);
            } else if (year.isPresent()) {
                if (year.get() <= 0) {
                    throw new InvalidStatisticsRequestException("Year parameter must be greater than 0.");
                }
                startDate = LocalDateTime.of(year.get(), 1, 1, 0, 0);
                endDate = startDate.with(TemporalAdjusters.lastDayOfYear()).plusDays(1).minusSeconds(1);
            } else {
                startDate = now.minusMonths(1);
            }
            return themeRepo.getThemeStatistics(startDate, endDate);
        } catch (InvalidStatisticsRequestException e) {
            throw e; // Re-throw known exceptions
        } catch (Exception e) {
            throw new ThemeStatisticsFetchException("Failed to fetch theme statistics from the repository.", e);
        }
    }
}
