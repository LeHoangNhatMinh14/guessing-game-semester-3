package assignment.individualtrack.player;

import assignment.individualtrack.business.exception.PlayerNotFoundException;
import assignment.individualtrack.business.impl.player.DeletePlayerUseCaseImpl;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeletePlayerUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepo;

    @InjectMocks
    private DeletePlayerUseCaseImpl deletePlayerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeletePlayerWhenPlayerExists() {
        Long playerId = 1L;
        PlayerEntity mockPlayer = new PlayerEntity(playerId, "John", 100, "123");

        // Mock existsById to return true, meaning player exists
        when(playerRepo.existsById(playerId)).thenReturn(true);

        // Mock findById to return the mock player when searched by ID
        when(playerRepo.findById(playerId)).thenReturn(Optional.of(mockPlayer));

        // Act: Delete player
        deletePlayerUseCase.deletePlayer(playerId);

        // Assert: Verify the deleteById is called once
        verify(playerRepo, times(1)).deleteById(playerId);
    }

    @Test
    void shouldThrowPlayerNotFoundExceptionWhenPlayerDoesNotExist() {
        Long playerId = 1L;

        // Mock existsById to return false, meaning player doesn't exist
        when(playerRepo.existsById(playerId)).thenReturn(false);

        // Act & Assert: Check that PlayerNotFoundException is thrown
        assertThrows(PlayerNotFoundException.class, () -> deletePlayerUseCase.deletePlayer(playerId));
    }

    @Test
    void shouldThrowPlayerNotFoundExceptionWhenPlayerIdIsNull() {
        // Act & Assert: Check that PlayerNotFoundException is thrown when playerId is null
        assertThrows(PlayerNotFoundException.class, () -> deletePlayerUseCase.deletePlayer(null));
    }

    @Test
    void shouldHandleDeleteForNonExistentPlayerGracefully() {
        // Arrange
        Long playerId = 999L;
        when(playerRepo.findById(playerId)).thenReturn(Optional.empty()); // Mock non-existence

        // Act & Assert
        try {
            deletePlayerUseCase.deletePlayer(playerId);
            fail("Expected PlayerNotFoundException to be thrown");
        } catch (PlayerNotFoundException e) {
            assertEquals("Player with ID " + playerId + " does not exist", e.getMessage());
        }

        verify(playerRepo, times(0)).deleteById(playerId); // Ensure deleteById is not called
    }

    @Test
    void shouldThrowExceptionWhenPlayerIdIsNull() {
        // Act & Assert
        PlayerNotFoundException exception = assertThrows(PlayerNotFoundException.class,
                () -> deletePlayerUseCase.deletePlayer(null));
        assertEquals("Player ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPlayerDoesNotExist() {
        // Arrange
        Long playerId = 999L; // Non-existent player ID
        when(playerRepo.existsById(playerId)).thenReturn(false);

        // Act & Assert
        PlayerNotFoundException exception = assertThrows(PlayerNotFoundException.class,
                () -> deletePlayerUseCase.deletePlayer(playerId));
        assertEquals("Player with ID 999 does not exist", exception.getMessage());
        verify(playerRepo, never()).deleteById(any());
    }

}

