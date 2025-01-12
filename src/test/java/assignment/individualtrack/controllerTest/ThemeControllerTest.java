package assignment.individualtrack.controllerTest;

import assignment.individualtrack.business.intefaces.AddWordToThemeUseCase;
import assignment.individualtrack.business.intefaces.GetAllThemeUseCase;
import assignment.individualtrack.domain.Themes.CreateThemeRequest;
import assignment.individualtrack.domain.Themes.GetAllThemesResponse;
import assignment.individualtrack.domain.Themes.Theme;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddWordToThemeUseCase addWordToThemeUseCase;

    @MockBean
    private GetAllThemeUseCase getAllThemesUseCase;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void addWordToTheme_ValidRequest_ShouldReturnOk() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());

        mockMvc.perform(multipart("/themes/words")
                        .file(image)
                        .param("themeId", "1")
                        .param("word", "SampleWord"))
                .andExpect(status().isOk())
                .andExpect(content().string("Word added to theme."));
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void createTheme_ValidRequest_ShouldReturnOk() throws Exception {
        CreateThemeRequest request = CreateThemeRequest.builder()
                .themeName("Nature")
                .build();

        mockMvc.perform(post("/themes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Theme created."));
    }

    @Test
    void getAllThemes_ShouldReturnThemeList() throws Exception {
        Theme theme1 = Theme.builder().id(1L).name("Nature").build();
        Theme theme2 = Theme.builder().id(2L).name("Space").build();

        GetAllThemesResponse response = GetAllThemesResponse.builder()
                .themes(List.of(theme1, theme2))
                .build();

        Mockito.when(getAllThemesUseCase.getAllThemes()).thenReturn(response);

        mockMvc.perform(get("/themes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.themes[0].id").value(1L))
                .andExpect(jsonPath("$.themes[0].name").value("Nature"))
                .andExpect(jsonPath("$.themes[1].id").value(2L))
                .andExpect(jsonPath("$.themes[1].name").value("Space"));
    }
}

