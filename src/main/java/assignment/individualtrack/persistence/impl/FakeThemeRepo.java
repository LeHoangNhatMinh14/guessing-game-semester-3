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
public class FakeThemeRepo implements ThemeRepo {
    private static long NEXT_ID = 1;
    private final List<ThemeEntity> themes = new ArrayList<>();

    @Override
    public boolean existsbyName(String name) {
        for (ThemeEntity theme : themes) {
            if (theme.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ThemeEntity save(ThemeEntity theme) {
        if (theme.getId() == null) {
            theme.setId(NEXT_ID++);
        } else {
            deletebyID(theme.getId());
        }
        themes.add(theme);
        return theme;
    }

    @Override
    public void deletebyID(long id) {
        themes.removeIf(theme -> theme.getId() == id);
    }

    @Override
    public Optional<ThemeEntity> findbyID(Long id) {
        return themes.stream()
                .filter(theme -> theme.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ThemeEntity> findAll() {
        return Collections.unmodifiableList(themes);
    }
}
