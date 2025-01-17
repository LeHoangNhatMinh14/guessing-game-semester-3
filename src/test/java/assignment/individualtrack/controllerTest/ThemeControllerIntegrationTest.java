package assignment.individualtrack.controllerTest;

import assignment.individualtrack.domain.Themes.CreateThemeRequest;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import assignment.individualtrack.persistence.entity.WordImage;
import assignment.individualtrack.business.impl.ImgurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ThemeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ThemeRepo themeRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImgurService imgurService; // Mock the ImgurService

    @BeforeEach
    void setUpDatabase() {
        // Clear database before each test
        themeRepo.deleteAll();

        // Initialize default themes with WordImage
        ThemeEntity natureTheme = ThemeEntity.builder()
                .name("Nature")
                .words(List.of(
                        new WordImage("Tree", "https://example.com/tree.png"),
                        new WordImage("Flower", "https://example.com/flower.png")
                ))
                .build();

        ThemeEntity spaceTheme = ThemeEntity.builder()
                .name("Space")
                .words(List.of(
                        new WordImage("Star", "https://example.com/star.png"),
                        new WordImage("Planet", "https://example.com/planet.png")
                ))
                .build();

        themeRepo.save(natureTheme);
        themeRepo.save(spaceTheme);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void addWordToTheme_ValidRequest_ShouldReturnOk() throws Exception {
        // Mock ImgurService behavior
        Mockito.when(imgurService.upload(Mockito.any()))
                .thenReturn("https://mocked.imgur.com/mountain.png");

        // Retrieve an existing theme from the database
        ThemeEntity theme = themeRepo.findByName("Nature").orElseThrow();

        String word = "Mountain";

        // Use a valid image for the test
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "mountain.png",
                MediaType.IMAGE_PNG_VALUE,
                "mock-image-content".getBytes() // Valid mock image content
        );

        mockMvc.perform(multipart("/themes/words")
                        .file(imageFile)
                        .param("themeId", String.valueOf(theme.getId()))
                        .param("word", word))
                .andExpect(status().isOk())
                .andExpect(content().string("Word added to theme."));

        // Verify the word and image URL were added to the theme in the database
        ThemeEntity updatedTheme = themeRepo.findById(theme.getId()).orElseThrow();
        assertThat(updatedTheme.getWords()).hasSize(3);
        WordImage addedWord = updatedTheme.getWords().stream()
                .filter(w -> w.getWord().equals("Mountain"))
                .findFirst()
                .orElseThrow();
        assertThat(addedWord.getImageUrl()).isEqualTo("https://mocked.imgur.com/mountain.png");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void createTheme_ValidRequest_ShouldReturnOk() throws Exception {
        CreateThemeRequest request = CreateThemeRequest.builder()
                .themeName("Ocean")
                .words(new ArrayList<>()) // Empty words list
                .build();

        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Theme created."));

        // Verify that the theme was created in the database
        ThemeEntity theme = themeRepo.findByName("Ocean").orElseThrow();
        assertThat(theme.getName()).isEqualTo("Ocean");
        assertThat(theme.getWords()).isEmpty();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getAllThemes_ShouldReturnThemeList() throws Exception {
        mockMvc.perform(get("/themes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.themes[0].name").value("Nature"))
                .andExpect(jsonPath("$.themes[0].words[0].word").value("Tree"))
                .andExpect(jsonPath("$.themes[1].name").value("Space"))
                .andExpect(jsonPath("$.themes[1].words[0].word").value("Star"));
    }
}
