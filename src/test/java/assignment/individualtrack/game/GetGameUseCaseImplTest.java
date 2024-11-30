package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.exception.GameNotFoundException;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.domain.Game.GetGameRequest;
import assignment.individualtrack.domain.Game.GetGameResponse;
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

class GetGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @InjectMocks
    private GetGameUseCaseImpl getGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGame_successful() {
        // Arrange
        Long gameId = 1L;
        GetGameRequest request = GetGameRequest.builder().gameID(gameId).build();
        GameEntity gameEntity = GameEntity.builder()
                .id(gameId)
                .score(100)
                .time(3600)
                .status(GameStatus.COMPLETED)
                .build();

        when(gameRepo.findById(gameId)).thenReturn(Optional.of(gameEntity));

        // Act
        GetGameResponse response = getGameUseCase.getGame(request);

        // Assert
        assertNotNull(response);
        assertEquals(gameId, response.getId());
        assertEquals(100, response.getScore());
        assertEquals(3600, response.getTime());
        assertEquals(GameStatus.COMPLETED, response.getStatus());

        verify(gameRepo, times(1)).findById(gameId);
    }

    @Test
    void getGame_notFound() {
        // Arrange
        Long gameId = 1L;
        GetGameRequest request = GetGameRequest.builder().gameID(gameId).build();

        when(gameRepo.findById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GameNotFoundException.class, () -> getGameUseCase.getGame(request));
        verify(gameRepo, times(1)).findById(gameId);
    }
}

