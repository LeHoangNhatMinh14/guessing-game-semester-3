package assignment.individualtrack.player;

import assignment.individualtrack.business.impl.player.LoginUseCaseImpl;
import assignment.individualtrack.domain.Player.LoginRequest;
import assignment.individualtrack.domain.Player.LoginResponse;
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

class LoginUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepository;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnResponseWhenCredentialsAreCorrect() {
        // Arrange
        String username = "PlayerOne";
        String password = "securepassword";
        PlayerEntity player = PlayerEntity.builder()
                .name(username)
                .password(password) // Assume plain text for simplicity in this test
                .build();

        when(playerRepository.findByName(username)).thenReturn(Optional.of(player));

        LoginRequest request = LoginRequest.builder()
                .name(username)
                .password(password)
                .build();

        // Act
        LoginResponse response = loginUseCase.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("Login successful", response.getMessage());
        assertEquals("dummy-token", response.getToken()); // Verify token logic
        verify(playerRepository, times(1)).findByName(username);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        // Arrange
        String username = "NonExistentPlayer";
        when(playerRepository.findByName(username)).thenReturn(Optional.empty());

        LoginRequest request = LoginRequest.builder()
                .name(username)
                .password("irrelevant")
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginUseCase.login(request));
        assertEquals("User not found", exception.getMessage());
        verify(playerRepository, times(1)).findByName(username);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        // Arrange
        String username = "PlayerOne";
        String correctPassword = "securepassword";
        String incorrectPassword = "wrongpassword";
        PlayerEntity player = PlayerEntity.builder()
                .name(username)
                .password(correctPassword) // Assume plain text for simplicity in this test
                .build();

        when(playerRepository.findByName(username)).thenReturn(Optional.of(player));

        LoginRequest request = LoginRequest.builder()
                .name(username)
                .password(incorrectPassword)
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loginUseCase.login(request));
        assertEquals("Invalid password", exception.getMessage());
        verify(playerRepository, times(1)).findByName(username);
    }
}
