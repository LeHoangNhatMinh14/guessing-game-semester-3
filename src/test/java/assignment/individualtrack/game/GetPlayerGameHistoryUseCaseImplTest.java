package assignment.individualtrack.game;

import assignment.individualtrack.business.impl.game.GetPlayerGameHistoryUseCaseImpl;
import assignment.individualtrack.business.intefaces.GetPlayerGameHistoryUseCase;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.domain.Game.GetPlayerGameHistoryRequest;
import assignment.individualtrack.domain.Game.GetPlayerGameHistoryResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetPlayerGameHistoryUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @InjectMocks
    private GetPlayerGameHistoryUseCaseImpl getPlayerGameHistoryUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPlayerGameHistory() {
        // Arrange
        long playerId = 1L;
        GetPlayerGameHistoryRequest request = GetPlayerGameHistoryRequest.builder().playerId(playerId).build();

        GameEntity game1 = GameEntity.builder().id(1L).score(100).time(1).status(GameStatus.COMPLETED).build();
        GameEntity game2 = GameEntity.builder().id(2L).score(150).time(1).status(GameStatus.COMPLETED).build();

        when(gameRepo.findTop20ByPlayerIdOrderByIdDesc(playerId)).thenReturn(List.of(game1, game2));

        // Act
        GetPlayerGameHistoryResponse response = getPlayerGameHistoryUseCase.getPlayerGameHistory(request);

        // Assert
        assertEquals(2, response.getGames().size());
        assertEquals(1L, response.getGames().get(0).getGameId());
        assertEquals(100, response.getGames().get(0).getScore());
        assertEquals(1, response.getGames().get(0).getTime());
        assertEquals(GameStatus.COMPLETED, response.getGames().get(0).getStatus());
    }
}
