package assignment.individualtrack.business.impl;

import assignment.individualtrack.domain.Theme;
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
