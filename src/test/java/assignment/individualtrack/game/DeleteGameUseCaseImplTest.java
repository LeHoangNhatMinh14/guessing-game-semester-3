package assignment.individualtrack.game;

import assignment.individualtrack.business.impl.game.DeleteGameUseCaseImpl;
import assignment.individualtrack.persistence.GameRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @InjectMocks
    private DeleteGameUseCaseImpl deleteGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteGameWhenGameIdExists() {
        // Arrange
        Long gameId = 1L;

        // Act
        deleteGameUseCase.deleteGame(gameId);

        // Assert
        verify(gameRepo, times(1)).deleteById(gameId);
    }

    @Test
    void shouldNotThrowExceptionWhenDeletingNonExistentGameId() {
        // Arrange
        Long gameId = 999L;

        // Act
        deleteGameUseCase.deleteGame(gameId);

        // Assert
        verify(gameRepo, times(1)).deleteById(gameId);
        // The method should not throw an exception even if the game ID doesn't exist.
    }

    @Test
    void shouldHandleNullGameIdGracefully() {
        // Arrange
        Long gameId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deleteGameUseCase.deleteGame(gameId));
        verifyNoInteractions(gameRepo); // Ensure no interaction occurs with the repository.
    }

    @Test
    void shouldCallDeleteByIdOnlyOnceForValidGameId() {
        // Arrange
        Long gameId = 5L;

        // Act
        deleteGameUseCase.deleteGame(gameId);

        // Assert
        verify(gameRepo, times(1)).deleteById(gameId);
    }
}
