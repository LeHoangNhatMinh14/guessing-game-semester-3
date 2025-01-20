package assignment.individualtrack.game;

import assignment.individualtrack.business.impl.game.CreateGameUseCaseImpl;
import assignment.individualtrack.business.impl.themes.PokemonService;
import assignment.individualtrack.domain.Game.GameStatus;
import assignment.individualtrack.domain.Game.StartGameRequest;
import assignment.individualtrack.domain.Game.StartGameResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.ThemeRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import assignment.individualtrack.persistence.entity.PlayerEntity;
import assignment.individualtrack.persistence.entity.ThemeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateGameUseCaseImplTest {

    @Mock
    private GameRepo gameRepo;

    @Mock
    private PlayerRepo playerRepo;

    @Mock
    private ThemeRepo themeRepo;

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private CreateGameUseCaseImpl createGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_happyPath_shouldCreateGameSuccessfully() {
        Long playerId = 1L;
        Long themeId = 2L;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(playerId);

        ThemeEntity themeEntity = new ThemeEntity();
        themeEntity.setId(themeId);

        StartGameRequest request = StartGameRequest.builder()
                .playerID(playerId)
                .themeID(themeId)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(playerEntity));
        when(themeRepo.findById(themeId)).thenReturn(Optional.of(themeEntity));

        ArgumentCaptor<GameEntity> gameEntityCaptor = ArgumentCaptor.forClass(GameEntity.class);

        GameEntity savedGameEntity = GameEntity.builder()
                .id(10L)
                .player(playerEntity)
                .theme(themeEntity)
                .score(0)
                .time(0)
                .status(GameStatus.IN_PROGRESS)
                .build();

        when(gameRepo.save(any(GameEntity.class))).thenReturn(savedGameEntity);

        StartGameResponse response = createGameUseCase.createGame(request);

        assertNotNull(response);
        assertEquals(10L, response.getGameId());
        assertEquals(playerId, response.getPlayerId());

        verify(playerRepo).findById(playerId);
        verify(themeRepo).findById(themeId);
        verify(gameRepo).save(gameEntityCaptor.capture());

        GameEntity capturedGameEntity = gameEntityCaptor.getValue();
        assertEquals(playerEntity, capturedGameEntity.getPlayer());
        assertEquals(themeEntity, capturedGameEntity.getTheme());
        assertEquals(0, capturedGameEntity.getScore());
        assertEquals(0, capturedGameEntity.getTime());
        assertEquals(GameStatus.IN_PROGRESS, capturedGameEntity.getStatus());
    }

    @Test
    void createGame_pokemonTheme_shouldUsePokemonService() {
        Long playerId = 1L;
        Long pokemonThemeId = 100L; // Predefined Pokemon theme ID
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(playerId);

        ThemeEntity themeEntity = new ThemeEntity();
        themeEntity.setId(pokemonThemeId);

        StartGameRequest request = StartGameRequest.builder()
                .playerID(playerId)
                .themeName("Pokemon")
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(playerEntity));
        when(pokemonService.getPokemonThemeId()).thenReturn(pokemonThemeId);
        when(themeRepo.findById(pokemonThemeId)).thenReturn(Optional.of(themeEntity));

        ArgumentCaptor<GameEntity> gameEntityCaptor = ArgumentCaptor.forClass(GameEntity.class);

        GameEntity savedGameEntity = GameEntity.builder()
                .id(10L)
                .player(playerEntity)
                .theme(themeEntity)
                .score(0)
                .time(0)
                .status(GameStatus.IN_PROGRESS)
                .build();

        when(gameRepo.save(any(GameEntity.class))).thenReturn(savedGameEntity);

        StartGameResponse response = createGameUseCase.createGame(request);

        assertNotNull(response);
        assertEquals(10L, response.getGameId());
        assertEquals(playerId, response.getPlayerId());

        verify(playerRepo).findById(playerId);
        verify(pokemonService).getPokemonThemeId();
        verify(themeRepo).findById(pokemonThemeId);
        verify(gameRepo).save(gameEntityCaptor.capture());
    }

    @Test
    void createGame_themeNotFound_shouldThrowException() {
        Long playerId = 1L;
        Long themeId = 2L;
        StartGameRequest request = StartGameRequest.builder()
                .playerID(playerId)
                .themeID(themeId)
                .build();

        when(playerRepo.findById(playerId)).thenReturn(Optional.of(new PlayerEntity()));
        when(themeRepo.findById(themeId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createGameUseCase.createGame(request));
        assertEquals("Theme not found with ID: " + themeId, exception.getMessage());

        verify(playerRepo).findById(playerId);
        verify(themeRepo).findById(themeId);
        verifyNoInteractions(gameRepo);
    }

    @Test
    void createGame_nullThemeId_shouldThrowException() {
        Long playerId = 1L;
        StartGameRequest request = StartGameRequest.builder()
                .playerID(playerId)
                .themeID(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createGameUseCase.createGame(request));
        assertEquals("Invalid or missing theme ID.", exception.getMessage());

        verifyNoInteractions(playerRepo);
        verifyNoInteractions(gameRepo);
    }

    @Test
    void createGame_invalidPlayerId_shouldThrowException() {
        StartGameRequest request = StartGameRequest.builder()
                .playerID(-1L)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createGameUseCase.createGame(request));
        assertEquals("Invalid or missing player ID.", exception.getMessage());

        verifyNoInteractions(playerRepo);
        verifyNoInteractions(gameRepo);
    }
}
