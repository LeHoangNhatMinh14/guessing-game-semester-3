package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.GetAllWordsofThemeUseCaseImpl;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemeRequest;
import assignment.individualtrack.domain.Themes.GetAllWordsofThemesResponse;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.entity.WordImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetAllWordsofThemeTest {

    @Mock
    private ThemeRepo themeRepo;

    @InjectMocks
    private GetAllWordsofThemeUseCaseImpl getAllWordsofThemeUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllWords_Success() {
        Long themeId = 1L;
        ThemeEntity themeEntity = ThemeEntity.builder()
                .id(themeId)
                .words(List.of(
                        new WordImage("apple", "banana-image-url"),
                        new WordImage("banana", "banana-image-url")
                ))
                .build();

        when(themeRepo.findById(themeId)).thenReturn(Optional.of(themeEntity));

        GetAllWordsofThemeRequest request = new GetAllWordsofThemeRequest(themeId,null);
        GetAllWordsofThemesResponse response = getAllWordsofThemeUseCase.getAllWords(request);

        assertEquals(2, response.getWords().size());
        assertEquals("apple", response.getWords().get(0).getWord());
        assertEquals("banana", response.getWords().get(1).getWord());
    }

    @Test
    void testGetAllWords_ThemeNotFound() {
        Long themeId = 1L;
        when(themeRepo.findById(themeId)).thenReturn(Optional.empty());

        GetAllWordsofThemeRequest request = new GetAllWordsofThemeRequest(themeId,null);
        GetAllWordsofThemesResponse response = getAllWordsofThemeUseCase.getAllWords(request);

        assertEquals(0, response.getWords().size());
    }
}
