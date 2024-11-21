package assignment.individualtrack.player;

import assignment.individualtrack.business.exception.InvalidPlayerException;
import assignment.individualtrack.business.impl.player.GetPlayerbyIdUseCaseImpl;
import assignment.individualtrack.domain.Player.GetPlayerRequest;
import assignment.individualtrack.domain.Player.GetPlayerResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetPlayerbyIdUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepo;

    @InjectMocks
    private GetPlayerbyIdUseCaseImpl getPlayerbyIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPlayerWhenPlayerExists() {
        // Arrange
        Long playerId = 1L;
        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(playerId)
                .name("Player One")
                .highscore(100)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(playerEntity));

        GetPlayerRequest request = GetPlayerRequest.builder()
                .playerId(playerId)
                .build();

        // Act
        GetPlayerResponse response = getPlayerbyIdUseCase.getPlayer(request);

        // Assert
        assertNotNull(response);
        assertEquals(playerId, response.getPlayerId());
        assertEquals("Player One", response.getName());
        assertEquals(100, response.getHighScore());
        verify(playerRepo, times(1)).findById(playerId);
    }

    @Test
    void shouldThrowExceptionWhenPlayerDoesNotExist() {
        // Arrange
        Long playerId = 999L;
        when(playerRepo.findById(playerId)).thenReturn(Optional.empty());

        GetPlayerRequest request = GetPlayerRequest.builder()
                .playerId(playerId)
                .build();

        // Act & Assert
        InvalidPlayerException exception = assertThrows(InvalidPlayerException.class, () -> getPlayerbyIdUseCase.getPlayer(request));
        assertEquals("400 BAD_REQUEST \"Player not found\"", exception.getMessage());
        verify(playerRepo, times(1)).findById(playerId);
    }
}
