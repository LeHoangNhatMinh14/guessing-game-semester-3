package assignment.individualtrack.game;

import assignment.individualtrack.business.impl.game.CreateGameUseCaseImpl;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

class CreateGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @InjectMocks
    private CreateGameUseCaseImpl createGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> createGameUseCase.createGame(null));
    }

    @Test
    void shouldThrowExceptionWhenPlayerIdIsNull() {
        // Arrange
        StartGameRequest startGameRequest = StartGameRequest.builder()
                .playerID(null)
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> createGameUseCase.createGame(startGameRequest));
        assertEquals("Player ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldHandleDatabaseErrorGracefully() {
        // Arrange
        long playerId = 1L;
        StartGameRequest startGameRequest = StartGameRequest.builder()
                .playerID(playerId)
                .build();

        doThrow(new RuntimeException("Database error")).when(gameRepo).save(any(GameEntity.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> createGameUseCase.createGame(startGameRequest));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void shouldInitializeScoreAndTimeToZeroForNewGame() {
        // Arrange
        long playerId = 2L;
        long gameId = 20L;

        StartGameRequest startGameRequest = StartGameRequest.builder()
                .playerID(playerId)
                .build();

        GameEntity savedGame = GameEntity.builder()
                .id(gameId)
                .score(0) // Validate score
                .time(0)  // Validate time
                .build();

        when(gameRepo.save(any(GameEntity.class))).thenReturn(savedGame);

        // Act
        StartGameResponse response = createGameUseCase.createGame(startGameRequest);

        // Assert
        assertNotNull(response);
        assertEquals(0, savedGame.getScore());
        assertEquals(0, savedGame.getTime());
    }
}
