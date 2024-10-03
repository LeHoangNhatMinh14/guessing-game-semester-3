package assignment.individualtrack.controller;

import assignment.individualtrack.business.intefaces.CreatePlayerUseCase;
import assignment.individualtrack.business.intefaces.DeletePlayerUseCase;
import assignment.individualtrack.business.intefaces.EditPlayerUseCase;
import assignment.individualtrack.domain.Player.CreatePlayerRequest;
import assignment.individualtrack.domain.Player.CreatePlayerResponse;
import assignment.individualtrack.domain.Player.EditPlayerRequest;
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

    @PostMapping()
    public ResponseEntity<CreatePlayerResponse> registerPlayer(@RequestBody CreatePlayerRequest request) {
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


}
