package assignment.individualtrack.game;

import assignment.individualtrack.business.exception.GameNotFoundException;
import assignment.individualtrack.business.impl.game.EndGameUseCaseImpl;
import assignment.individualtrack.domain.Game.*;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EndGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @InjectMocks
    private EndGameUseCaseImpl endGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void endGame_success() {
        // Arrange
        EndGameRequest request = EndGameRequest.builder()
                .gameId(1L)
                .correctGuesses(5)
                .incorrectGuesses(2)
                .time(120)
                .status(GameStatus.COMPLETED)
                .build();

        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(1L);
        gameEntity.setStatus(GameStatus.IN_PROGRESS);

        when(gameRepo.findById(1L)).thenReturn(Optional.of(gameEntity));
        when(gameRepo.save(any(GameEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        EndGameResponse response = endGameUseCase.endGame(request);

        // Assert
        assertNotNull(response);
        assertEquals(3, response.getFinalScore()); // 5 correct - 2 incorrect = 3
        assertEquals(120, response.getTimeTaken());
        assertEquals(5, response.getCorrectGuesses());
        assertEquals(2, response.getIncorrectGuesses());
        assertEquals("Game completed successfully!", response.getMessage());
        verify(gameRepo, times(1)).save(any(GameEntity.class));
    }

    @Test
    void endGame_gameNotFound() {
        // Arrange
        EndGameRequest request = EndGameRequest.builder()
                .gameId(1L)
                .build();

        when(gameRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(GameNotFoundException.class, () -> endGameUseCase.endGame(request));

        // Assert exception properties
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()); // Use getStatusCode() instead of getStatus()
        assertEquals("Game not found with ID: 1", exception.getReason());
        verify(gameRepo, never()).save(any(GameEntity.class));
    }

    @Test
    void endGame_alreadyCompleted() {
        // Arrange
        EndGameRequest request = EndGameRequest.builder()
                .gameId(1L)
                .status(GameStatus.COMPLETED)
                .build();

        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(1L);
        gameEntity.setStatus(GameStatus.COMPLETED);

        when(gameRepo.findById(1L)).thenReturn(Optional.of(gameEntity));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> endGameUseCase.endGame(request));
        assertEquals("Game is already completed.", exception.getMessage());
        verify(gameRepo, never()).save(any(GameEntity.class));
    }

    @Test
    void endGame_negativeGuesses_shouldCalculateScoreCorrectly() {
        // Arrange
        EndGameRequest request = EndGameRequest.builder()
                .gameId(1L)
                .correctGuesses(3)
                .incorrectGuesses(5)
                .time(150)
                .status(GameStatus.COMPLETED)
                .build();

        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(1L);
        gameEntity.setStatus(GameStatus.IN_PROGRESS);

        when(gameRepo.findById(1L)).thenReturn(Optional.of(gameEntity));
        when(gameRepo.save(any(GameEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        EndGameResponse response = endGameUseCase.endGame(request);

        // Assert
        assertNotNull(response);
        assertEquals(0, response.getFinalScore()); // 3 correct - 5 incorrect = -2
        assertEquals(150, response.getTimeTaken());
        assertEquals(3, response.getCorrectGuesses());
        assertEquals(5, response.getIncorrectGuesses());
        verify(gameRepo, times(1)).save(any(GameEntity.class));
    }
}
