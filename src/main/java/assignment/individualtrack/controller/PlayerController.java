package assignment.individualtrack.controller;

import assignment.individualtrack.business.intefaces.CreatePlayerUseCase;
import assignment.individualtrack.business.intefaces.DeletePlayerUseCase;
import assignment.individualtrack.business.intefaces.EditPlayerUseCase;
import assignment.individualtrack.business.intefaces.GetPlayerUseCase;
import assignment.individualtrack.domain.Player.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {
    private final CreatePlayerUseCase createPlayerUseCase;
    private final EditPlayerUseCase editPlayerUseCase;
    private final DeletePlayerUseCase deletePlayerUseCase;
    private final GetPlayerUseCase getPlayerUseCase;

    @PostMapping()
    public ResponseEntity<CreatePlayerResponse> registerPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        System.out.println("Received Request: " + request);
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreatePlayerResponse> updatePlayer(@PathVariable long id,@RequestBody EditPlayerRequest request) {
        request.setId(id);
        editPlayerUseCase.editPlayer(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable long id) {
        deletePlayerUseCase.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPlayerResponse> getPlayer(@PathVariable long id) {
        GetPlayerRequest request = GetPlayerRequest.builder().playerId(id).build();
        GetPlayerResponse response = getPlayerUseCase.getPlayer(request);
        return ResponseEntity.ok(response);
    }
}
