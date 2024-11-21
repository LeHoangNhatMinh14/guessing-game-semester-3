package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepo extends JpaRepository<ThemeEntity, Long> {
    boolean existsByName(String name);
    Optional<ThemeEntity> findById(Long id);
    void deleteById(Long id);
    List<ThemeEntity> findAll();
}
