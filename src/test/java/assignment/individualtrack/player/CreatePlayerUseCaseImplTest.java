package assignment.individualtrack.player;

import assignment.individualtrack.business.impl.player.CreatePlayerUseCaseImpl;
import assignment.individualtrack.domain.Player.CreatePlayerRequest;
import assignment.individualtrack.domain.Player.CreatePlayerResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.Role;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreatePlayerUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreatePlayerUseCaseImpl createPlayerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayer_successful() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("NewPlayer")
                .password("password123")
                .build();

        when(playerRepo.existsByName(request.getName())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        PlayerEntity savedPlayer = PlayerEntity.builder()
                .id(1L)
                .name(request.getName())
                .password("encodedPassword")
                .role(Role.USER)
                .highscore(0)
                .build();
        when(playerRepo.save(any(PlayerEntity.class))).thenReturn(savedPlayer);

        // Act
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getPlayerId());
        verify(playerRepo, times(1)).existsByName(request.getName());
        verify(playerRepo, times(1)).save(any(PlayerEntity.class));
    }

    @Test
    void createPlayer_nameAlreadyExists() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("ExistingPlayer")
                .password("password123")
                .build();

        when(playerRepo.existsByName(request.getName())).thenReturn(true);

        // Act
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        // Assert
        assertNull(response);
        verify(playerRepo, times(1)).existsByName(request.getName());
        verify(playerRepo, never()).save(any(PlayerEntity.class));
    }

    @Test
    void createPlayer_passwordTooShort() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("NewPlayer")
                .password("123") // Too short password
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createPlayerUseCase.createPlayer(request));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
        verify(playerRepo, never()).existsByName(any());
        verify(playerRepo, never()).save(any(PlayerEntity.class));
    }

    @Test
    void createPlayer_nullRequest() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createPlayerUseCase.createPlayer(null));
        assertEquals("Player request cannot be null", exception.getMessage());
        verify(playerRepo, never()).existsByName(any());
        verify(playerRepo, never()).save(any(PlayerEntity.class));
    }

    @Test
    void createPlayer_blankPassword() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("NewPlayer")
                .password("") // Blank password
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createPlayerUseCase.createPlayer(request));
        assertEquals("Password cannot be null or empty", exception.getMessage());
        verify(playerRepo, never()).existsByName(any());
        verify(playerRepo, never()).save(any(PlayerEntity.class));
    }

    @Test
    void createPlayer_blankName() {
        // Arrange
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .name("") // Blank name
                .password("password123")
                .build();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createPlayerUseCase.createPlayer(request));
        assertEquals("Player name cannot be null or empty", exception.getMessage());
        verify(playerRepo, never()).existsByName(any());
        verify(playerRepo, never()).save(any(PlayerEntity.class));
    }
}
