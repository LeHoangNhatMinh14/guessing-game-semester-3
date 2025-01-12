package assignment.individualtrack.controllerTest;

import assignment.individualtrack.business.intefaces.CreateGameUseCase;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateGameUseCase createGameUseCase;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void startGame_ValidRequest_ShouldReturnOk() throws Exception {
        StartGameRequest request = StartGameRequest.builder()
                .playerID(1L)
                .themeID(10L)
                .themeName("Nature")
                .build();

        StartGameResponse response = StartGameResponse.builder()
                .gameId(100L)
                .playerId(1L)
                .build();

        Mockito.when(createGameUseCase.createGame(any(StartGameRequest.class))).thenReturn(response);

        mockMvc.perform(post("/games/startNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(100L))
                .andExpect(jsonPath("$.playerId").value(1L));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void startGame_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        // Prepare an invalid request with missing fields
        StartGameRequest request = StartGameRequest.builder()
                .themeID(10L)
                .themeName("Nature")
                .build(); // playerID is missing

        // Perform the test
        mockMvc.perform(post("/games/startNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
