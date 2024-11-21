package assignment.individualtrack.game;

import assignment.individualtrack.business.exception.GameNotFoundException;
import assignment.individualtrack.business.impl.game.EndGameUseCaseImpl;
import assignment.individualtrack.domain.Game.EndGameRequest;
import assignment.individualtrack.domain.Game.EndGameResponse;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @InjectMocks
    private EndGameUseCaseImpl endGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateGameAndReturnResponseWhenGameExistsAndIsNotCompleted() {
        // Arrange
        long gameId = 1L;
        int score = 100;
        int time = 300;
        int correctGuesses = 10;
        int incorrectGuesses = 2;

        EndGameRequest endGameRequest = EndGameRequest.builder()
                .gameId(gameId)
                .score(score)
                .time(time)
                .correctGuesses(correctGuesses)
                .incorrectGuesses(incorrectGuesses)
                .build();

        GameEntity gameEntity = GameEntity.builder()
                .id(gameId)
                .status(GameStatus.IN_PROGRESS)
                .build();

        when(gameRepo.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameRepo.save(any(GameEntity.class))).thenReturn(gameEntity);

        // Act
        EndGameResponse response = endGameUseCase.endGame(endGameRequest);

        // Assert
        assertNotNull(response);
        assertEquals(score, response.getFinalScore());
        assertEquals(time, response.getTimeTaken());
        assertEquals(correctGuesses, response.getCorrectGuesses());
        assertEquals(incorrectGuesses, response.getIncorrectGuesses());
        assertEquals("Game completed successfully!", response.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist() {
        // Arrange
        Long gameId = 1L;

        EndGameRequest endGameRequest = EndGameRequest.builder()
                .gameId(gameId)
                .build();

        when(gameRepo.findById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        GameNotFoundException exception = assertThrows(GameNotFoundException.class,
                () -> endGameUseCase.endGame(endGameRequest));
        assertEquals("404 NOT_FOUND \"Game not found\"", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenGameIsAlreadyCompleted() {
        // Arrange
        Long gameId = 1L;

        EndGameRequest endGameRequest = EndGameRequest.builder()
                .gameId(gameId)
                .build();

        GameEntity gameEntity = GameEntity.builder()
                .id(gameId)
                .status(GameStatus.COMPLETED)
                .build();

        when(gameRepo.findById(gameId)).thenReturn(Optional.of(gameEntity));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> endGameUseCase.endGame(endGameRequest));
        assertEquals("Game has already been completed.", exception.getMessage());
    }

    @Test
    void shouldSaveUpdatedGameEntityWithFinalValues() {
        // Arrange
        long gameId = 1L;
        int score = 120;
        int time = 250;

        EndGameRequest endGameRequest = EndGameRequest.builder()
                .gameId(gameId)
                .score(score)
                .time(time)
                .build();

        GameEntity gameEntity = GameEntity.builder()
                .id(gameId)
                .status(GameStatus.IN_PROGRESS)
                .build();

        when(gameRepo.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameRepo.save(any(GameEntity.class))).thenReturn(gameEntity);

        // Act
        endGameUseCase.endGame(endGameRequest);

        // Assert
        verify(gameRepo).save(argThat(savedEntity ->
                savedEntity.getScore() == score &&
                        savedEntity.getTime() == time &&
                        savedEntity.getStatus() == GameStatus.COMPLETED));
    }

    @Test
    void shouldHandleNullRequestGracefully() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> endGameUseCase.endGame(null));
    }
}
