package assignment.individualtrack.persistence;

import assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ThemeRepo extends JpaRepository<ThemeEntity, Long> {
    boolean existsByName(String name);
    Optional<ThemeEntity> findByName(String name);
    boolean existsByIdAndWordsContaining(Long id, String word);

    @Query("SELECT new assignment.individualtrack.domain.Themes.GetThemeStatisticsResponse(t.name, COUNT(g.id)) " +
            "FROM GameEntity g " +
            "JOIN g.theme t " +
            "WHERE g.playedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY t.name " +
            "ORDER BY COUNT(g.id) DESC")
    List<GetThemeStatisticsResponse> getThemeStatistics(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Case-insensitive search
    List<ThemeEntity> findByNameContainingIgnoreCase(String name);

    // Custom query for advanced filtering
    @Query("SELECT t FROM ThemeEntity t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ThemeEntity> searchThemesByName(@Param("name") String name);
}

