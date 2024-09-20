package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;

import java.util.List;
import java.util.Optional;

public interface ThemeRepo {
    boolean existsbyName(String name);
    ThemeEntity save(ThemeEntity player);
    void deletebyID(long id);
    Optional<ThemeEntity> findbyID(Long id);
    List<ThemeEntity> findAll();
}
