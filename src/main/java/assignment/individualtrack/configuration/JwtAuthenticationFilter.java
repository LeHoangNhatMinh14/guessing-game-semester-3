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
                // Parse JWT token
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY) // Validate with the same secret key
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // Extract claims
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                Long playerId = claims.get("id", Long.class);

                if (username != null && role != null && playerId != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Create authorities from role
                    List<org.springframework.security.core.GrantedAuthority> authorities = List.of(() -> role);

                    // Set up authentication
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorities
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                // Clear the security context on JWT validation failure
                SecurityContextHolder.clearContext();

                // Log the error for debugging purposes
                System.err.println("Invalid JWT token: " + e.getMessage());

                // Optionally send a 401 response (uncomment if needed)
                // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
