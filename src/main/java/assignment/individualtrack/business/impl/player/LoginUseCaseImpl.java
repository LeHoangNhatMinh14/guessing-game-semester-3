package assignment.individualtrack.business.impl.player;

import assignment.individualtrack.business.intefaces.LoginUseCase;
import assignment.individualtrack.domain.Player.LoginRequest;
import assignment.individualtrack.domain.Player.LoginResponse;
import assignment.individualtrack.persistence.PlayerRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final PlayerRepo playerRepository;
    private final PasswordEncoder passwordEncoder;

    // Ensure this key is long and random enough for HS256
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "your_secret_key_here_your_secret_key_here".getBytes()
    );

    @Override
    public LoginResponse login(LoginRequest request) {
        // Fetch user by username
        var player = playerRepository.findByName(request.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), player.getPassword())) {
            System.out.println("Invalid login attempt for username: " + request.getName());
            throw new BadCredentialsException("Invalid password");
        }

        // Generate a JWT token
        String token = generateToken(player.getName(), "ROLE_" + player.getRole().name());

        return new LoginResponse("Login successful", token);
    }


    private String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add ROLE_ prefix here

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

}
