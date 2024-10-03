package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.domain.Themes.Theme;
import assignment.individualtrack.persistence.entity.ThemeEntity;

final class ThemeConverter {
    private ThemeConverter() {

    }

    public static Theme convert(ThemeEntity themeEntity) {
        return Theme.builder()
                .id(themeEntity.getId())
                .name(themeEntity.getName())
                .words(themeEntity.getWords())
                .build();
    }
}
