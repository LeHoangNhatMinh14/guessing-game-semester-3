package assignment.individualtrack.controller;

import assignment.individualtrack.business.intefaces.*;
import assignment.individualtrack.domain.Game.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final EndGameUseCase endGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final GetPlayerGameHistoryUseCase getPlayerGameHistoryUseCase;

    // Endpoint to start a new game
    @PostMapping("/startNew")
    public ResponseEntity<StartGameResponse> startGame(@Valid @RequestBody StartGameRequest startGameRequest) {
        if (startGameRequest.getPlayerID() == null || startGameRequest.getPlayerID() <= 0) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request
        }
        StartGameResponse response = createGameUseCase.createGame(startGameRequest);
        return ResponseEntity.ok(response);
    }


    // Endpoint to end the game and save the result
    @PostMapping("/end")
    public ResponseEntity<EndGameResponse> endGame(@RequestBody EndGameRequest endGameRequest) {
        EndGameResponse response = endGameUseCase.endGame(endGameRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GetGameResponse> getGame(@PathVariable long gameId) {
        GetGameRequest request = GetGameRequest.builder()
                .gameID(gameId)
                .build();
        GetGameResponse response = getGameUseCase.getGame(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/player/{playerId}/history")
    public ResponseEntity<GetPlayerGameHistoryResponse> getPlayerGameHistory(@PathVariable long playerId) {
        GetPlayerGameHistoryRequest request = GetPlayerGameHistoryRequest.builder()
                .playerId(playerId)
                .build();

        GetPlayerGameHistoryResponse response = getPlayerGameHistoryUseCase.getPlayerGameHistory(request);
        return ResponseEntity.ok(response);
    }
}
