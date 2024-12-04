package assignment.individualtrack.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("your_secret_key_here_your_secret_key_here".getBytes());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY) // Use the same secret key used for encoding
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject(); // Retrieve the subject (username)
                String role = claims.get("role", String.class); // Retrieve the role claim
                Long playerId = claims.get("id", Long.class); // Retrieve the player ID

                if (username != null && role != null && playerId != null) {
                    List<org.springframework.security.core.GrantedAuthority> authorities = List.of(() -> role);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorities
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the SecurityContext with authentication
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                // Log exception for better debugging
                System.err.println("Failed to parse JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
