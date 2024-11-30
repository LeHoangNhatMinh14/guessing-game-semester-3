package assignment.individualtrack.player;

import assignment.individualtrack.business.impl.player.LoginUseCaseImpl;
import assignment.individualtrack.domain.Player.LoginRequest;
import assignment.individualtrack.domain.Player.LoginResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginUseCaseImplTest {

    @Mock
    private PlayerRepo playerRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_successful() {
        // Arrange
        LoginRequest request = new LoginRequest("validUser", "validPassword");

        PlayerEntity player = PlayerEntity.builder()
                .name("validUser")
                .password("encodedPassword")
                .role(assignment.individualtrack.persistence.Role.USER) // Added role to prevent NPE
                .build();

        when(playerRepo.findByName(request.getName())).thenReturn(java.util.Optional.of(player));
        when(passwordEncoder.matches(request.getPassword(), player.getPassword())).thenReturn(true);

        // Act
        LoginResponse response = loginUseCase.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("Login successful", response.getMessage());
        assertNotNull(response.getToken());
    }

    @Test
    void login_userNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest("unknownUser", "password");

        when(playerRepo.findByName(request.getName())).thenReturn(java.util.Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> loginUseCase.login(request));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void login_invalidPassword() {
        // Arrange
        LoginRequest request = new LoginRequest("validUser", "wrongPassword");

        PlayerEntity player = PlayerEntity.builder()
                .name("validUser")
                .password("encodedPassword")
                .role(assignment.individualtrack.persistence.Role.USER) // Added role to prevent NPE
                .build();

        when(playerRepo.findByName(request.getName())).thenReturn(java.util.Optional.of(player));
        when(passwordEncoder.matches(request.getPassword(), player.getPassword())).thenReturn(false);

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> loginUseCase.login(request));
        assertEquals("Invalid password", exception.getMessage());
    }
}
