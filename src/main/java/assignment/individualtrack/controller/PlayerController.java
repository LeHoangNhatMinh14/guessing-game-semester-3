package assignment.individualtrack.controller;

import assignment.individualtrack.business.intefaces.*;
import assignment.individualtrack.domain.Player.*;
import assignment.individualtrack.persistence.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {
    private final CreatePlayerUseCase createPlayerUseCase;
    private final EditPlayerUseCase editPlayerUseCase;
    private final DeletePlayerUseCase deletePlayerUseCase;
    private final GetPlayerbyIdUseCase getPlayerbyIdUseCase;
    private final LoginUseCase loginUseCase;
    private final PasswordEncoder passwordEncoder;
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("your_secret_key_here_your_secret_key_here".getBytes());

    @PostMapping("/register")
    public ResponseEntity<CreatePlayerResponse> registerPlayer(@Valid @RequestBody CreatePlayerRequest request) {
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreatePlayerResponse> updatePlayer(@PathVariable long id, @RequestBody EditPlayerRequest request) {
        request.setId(id);
        editPlayerUseCase.editPlayer(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlayer(@PathVariable long id) {
        deletePlayerUseCase.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPlayerResponse> getPlayer(@PathVariable long id) {
        GetPlayerRequest request = GetPlayerRequest.builder().playerId(id).build();
        GetPlayerResponse response = getPlayerbyIdUseCase.getPlayer(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginUseCase.login(request);
        return ResponseEntity.ok(response);
    }
}