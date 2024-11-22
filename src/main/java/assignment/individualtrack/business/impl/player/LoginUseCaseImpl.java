package assignment.individualtrack.business.impl.player;

import assignment.individualtrack.business.intefaces.LoginUseCase;
import assignment.individualtrack.domain.Player.LoginRequest;
import assignment.individualtrack.domain.Player.LoginResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final PlayerRepo playerRepository;
//    private final PasswordEncoder passwordEncoder; // Spring Security Password Encoder

    @Override
    public LoginResponse login(LoginRequest request) {
        System.out.println("Nah Your here");
        var player = playerRepository.findByName(request.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("Your here");
//        if (!passwordEncoder.matches(request.getPassword(), player.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
        if (!Objects.equals(request.getPassword(), player.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        System.out.println("Just kidding Your here");
        // Generate a token (e.g., JWT) or session identifier here if needed
        String token = "dummy-token"; // Replace with actual token generation logic

        return new LoginResponse("Login successful", token);
    }
}
