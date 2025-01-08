package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.entity.WordImage;

import java.util.ArrayList;
import java.util.List;

final class ThemeConverter {
    private ThemeConverter() {
    }

    public static Theme convert(ThemeEntity themeEntity) {
        if (themeEntity == null) {
            return null;
        }

        return Theme.builder()
                .id(themeEntity.getId())
                .name(themeEntity.getName())
                .words(themeEntity.getWords() != null
                        ? new ArrayList<>(themeEntity.getWords()) // Direct mapping of WordImage objects
                        : new ArrayList<>())
                .build();
    }

    public static ThemeEntity convertToEntity(Theme theme) {
        if (theme == null) {
            return null;
        }

        return ThemeEntity.builder()
                .id(theme.getId())
                .name(theme.getName())
                .words(theme.getWords() != null
                        ? new ArrayList<>(theme.getWords()) // Direct mapping of WordImage objects
                        : new ArrayList<>())
                .build();
    }
}
