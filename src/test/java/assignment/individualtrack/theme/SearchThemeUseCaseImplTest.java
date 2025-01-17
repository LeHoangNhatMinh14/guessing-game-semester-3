package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.SearchThemeUseCaseImpl;
import assignment.individualtrack.domain.Themes.SearchThemeRequest;
import assignment.individualtrack.domain.Themes.SearchThemeResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchThemeUseCaseImplTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private SearchThemeUseCaseImpl searchThemeUseCase;

}

