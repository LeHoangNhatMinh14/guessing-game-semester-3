package assignment.individualtrack.game;

import assignment.individualtrack.business.impl.game.CreateGameUseCaseImpl;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @Mock
    private PlayerRepo playerRepo;

    @InjectMocks
    private CreateGameUseCaseImpl createGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_happyPath_shouldCreateGameSuccessfully() {
        // Arrange
        Long playerId = 1L;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(playerId);

        StartGameRequest request = StartGameRequest.builder()
                .playerID(playerId)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(playerEntity));

        ArgumentCaptor<GameEntity> gameEntityCaptor = ArgumentCaptor.forClass(GameEntity.class);

        GameEntity savedGameEntity = GameEntity.builder()
                .id(10L)
                .player(playerEntity)
                .score(0)
                .time(0)
                .status(GameStatus.IN_PROGRESS)
                .build();

        when(gameRepo.save(any(GameEntity.class))).thenReturn(savedGameEntity);

        // Act
        StartGameResponse response = createGameUseCase.createGame(request);

        // Assert
        assertNotNull(response);
        assertEquals(10L, response.getGameId());
        assertEquals(playerId, response.getPlayerId());

        verify(playerRepo).findById(playerId);
        verify(gameRepo).save(gameEntityCaptor.capture());

        GameEntity capturedGameEntity = gameEntityCaptor.getValue();
        assertEquals(playerEntity, capturedGameEntity.getPlayer());
        assertEquals(0, capturedGameEntity.getScore());
        assertEquals(0, capturedGameEntity.getTime());
        assertEquals(GameStatus.IN_PROGRESS, capturedGameEntity.getStatus());
    }

    @Test
    void createGame_nullPlayerId_shouldThrowException() {
        // Arrange
        StartGameRequest request = StartGameRequest.builder()
                .playerID(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createGameUseCase.createGame(request));
        assertEquals("Player ID cannot be null", exception.getMessage());

        verifyNoInteractions(playerRepo);
        verifyNoInteractions(gameRepo);
    }

    @Test
    void createGame_playerNotFound_shouldThrowException() {
        // Arrange
        Long playerId = 1L;
        StartGameRequest request = StartGameRequest.builder()
                .playerID(playerId)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createGameUseCase.createGame(request));
        assertEquals("Player not found with ID: " + playerId, exception.getMessage());

        verify(playerRepo).findById(playerId);
        verifyNoInteractions(gameRepo);
    }
}
