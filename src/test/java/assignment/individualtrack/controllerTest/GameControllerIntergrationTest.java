package assignment.individualtrack.controllerTest;

import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.Role;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GameControllerIntergrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private ThemeRepo themeRepo;

    @BeforeEach
    void setUp() {
        // Clear previous test data
        gameRepo.deleteAll();
        themeRepo.deleteAll();
        playerRepo.deleteAll();

        // Create and save a test player
        PlayerEntity player = PlayerEntity.builder()
                .name("testuser")
                .password("password123")
                .highscore(0)
                .role(Role.USER)
                .build();
        playerRepo.save(player);

        // Create and save a test theme
        ThemeEntity theme = ThemeEntity.builder()
                .name("Nature")
                .build();
        themeRepo.save(theme);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void startGame_ValidRequest_ShouldReturnOk() throws Exception {
        // Retrieve player and theme
        PlayerEntity player = playerRepo.findByName("testuser").orElseThrow();
        ThemeEntity theme = themeRepo.findByName("Nature").orElseThrow();

        // Prepare a valid start game request
        StartGameRequest request = StartGameRequest.builder()
                .playerID(player.getId())
                .themeID(theme.getId())
                .themeName("Nature")
                .build();

        // Perform the test
        mockMvc.perform(post("/games/startNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").isNotEmpty())
                .andExpect(jsonPath("$.playerId").value(player.getId()));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void startGame_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Prepare an invalid request with missing playerID
        StartGameRequest request = StartGameRequest.builder()
                .themeID(10L) // Non-existent theme ID
                .themeName("Nature")
                .build();

        // Perform the test
        mockMvc.perform(post("/games/startNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
