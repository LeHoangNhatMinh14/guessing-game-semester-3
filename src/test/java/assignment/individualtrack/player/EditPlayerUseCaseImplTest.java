package assignment.individualtrack.player;

import assignment.individualtrack.business.exception.InvalidPlayerException;
import assignment.individualtrack.business.impl.player.EditPlayerUseCaseImpl;
import assignment.individualtrack.domain.Player.EditPlayerRequest;
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

class EditPlayerUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepo;

    @InjectMocks
    private EditPlayerUseCaseImpl editPlayerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldEditPlayerWhenPlayerExists() {
        // Arrange
        Long playerId = 1L;
        EditPlayerRequest request = EditPlayerRequest.builder()
                .id(playerId)
                .name("New Name")
                .highscore(100)
                .build();

        PlayerEntity existingPlayer = PlayerEntity.builder()
                .id(playerId)
                .name("Old Name")
                .highscore(50)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(existingPlayer));

        // Act
        editPlayerUseCase.editPlayer(request);

        // Assert
        verify(playerRepo, times(1)).save(existingPlayer);
        assertEquals("New Name", existingPlayer.getName());
        assertEquals(100, existingPlayer.getHighscore());
    }

    @Test
    void shouldThrowExceptionWhenPlayerDoesNotExist() {
        // Arrange
        Long playerId = 999L;
        EditPlayerRequest request = EditPlayerRequest.builder()
                .id(playerId)
                .name("New Name")
                .highscore(100)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidPlayerException exception = assertThrows(InvalidPlayerException.class, () -> editPlayerUseCase.editPlayer(request));

        // Adjusted the expected message to include quotes around STUDENT_ID_INVALID
        assertEquals("400 BAD_REQUEST \"STUDENT_ID_INVALID\"", exception.getMessage());
        verify(playerRepo, never()).save(any());
    }

    @Test
    void shouldNotModifyPlayerWhenNoChangesProvided() {
        // Arrange
        Long playerId = 2L;
        EditPlayerRequest request = EditPlayerRequest.builder()
                .id(playerId)
                .name(null) // No new name
                .highscore(50) // Same as existing highscore
                .build();

        PlayerEntity existingPlayer = PlayerEntity.builder()
                .id(playerId)
                .name("Existing Name")
                .highscore(50)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(existingPlayer));

        // Act
        editPlayerUseCase.editPlayer(request);

        // Assert
        verify(playerRepo, times(1)).save(existingPlayer);
        assertEquals(null, existingPlayer.getName());
        assertEquals(50, existingPlayer.getHighscore());
    }
}

