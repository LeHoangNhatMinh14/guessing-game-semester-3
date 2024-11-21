package assignment.individualtrack.player;

import assignment.individualtrack.business.impl.player.CreatePlayerUseCaseImpl;
import assignment.individualtrack.domain.Player.CreatePlayerRequest;
import assignment.individualtrack.domain.Player.CreatePlayerResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreatePlayerUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepo;

    @InjectMocks
    private CreatePlayerUseCaseImpl createPlayerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewPlayerWhenNameDoesNotExist() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("NewPlayer")
                .password("securepassword")
                .build();

        PlayerEntity savedPlayer = PlayerEntity.builder()
                .id(1L)
                .name("NewPlayer")
                .password("securepassword")
                .highscore(0)
                .build();

        when(playerRepo.existsByName(request.getName())).thenReturn(false);
        when(playerRepo.save(any(PlayerEntity.class))).thenReturn(savedPlayer);

        // Act
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getPlayerId());
        verify(playerRepo, times(1)).existsByName("NewPlayer");
        verify(playerRepo, times(1)).save(any(PlayerEntity.class));
    }

    @Test
    void shouldReturnNullWhenNameAlreadyExists() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("ExistingPlayer")
                .password("securepassword")
                .build();

        when(playerRepo.existsByName(request.getName())).thenReturn(true);

        // Act
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        // Assert
        assertNull(response);
        verify(playerRepo, times(1)).existsByName("ExistingPlayer");
        verify(playerRepo, never()).save(any(PlayerEntity.class));
    }

    @Test
    void shouldSetHighScoreToZeroForNewPlayer() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("AnotherPlayer")
                .password("anotherpassword")
                .build();

        PlayerEntity savedPlayer = PlayerEntity.builder()
                .id(2L)
                .name("AnotherPlayer")
                .password("anotherpassword")
                .highscore(0)
                .build();

        when(playerRepo.existsByName(request.getName())).thenReturn(false);
        when(playerRepo.save(any(PlayerEntity.class))).thenReturn(savedPlayer);

        // Act
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        // Assert
        assertNotNull(response);
        assertEquals(2L, response.getPlayerId());
        verify(playerRepo, times(1)).save(any(PlayerEntity.class));
        assertEquals(0, savedPlayer.getHighscore());
    }
}

