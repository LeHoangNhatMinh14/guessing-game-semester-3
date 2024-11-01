package assignment.individualtrack.persistence.impl;

import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeThemeRepo {
    private static long NEXT_ID = 1;
    private final List<ThemeEntity> themes = new ArrayList<>();

    public boolean existsbyName(String name) {
        for (ThemeEntity theme : themes) {
            if (theme.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public ThemeEntity save(ThemeEntity theme) {
        if (theme.getId() == null) {
            theme.setId(NEXT_ID++);
        } else {
            deletebyID(theme.getId());
        }
        themes.add(theme);
        return theme;
    }

    public void deletebyID(long id) {
        themes.removeIf(theme -> theme.getId() == id);
    }

    public Optional<ThemeEntity> findbyID(Long id) {
        return themes.stream()
                .filter(theme -> theme.getId().equals(id))
                .findFirst();
    }

    public List<ThemeEntity> findAll() {
        return Collections.unmodifiableList(themes);
    }
}
