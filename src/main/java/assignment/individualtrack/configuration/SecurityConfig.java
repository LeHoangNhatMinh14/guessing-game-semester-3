package assignment.individualtrack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    // Password encoder for hashing and verifying passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expose AuthenticationManager as a bean to support custom authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Spring Security filter chain configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/players/login", "/players/register").permitAll() // Allow login and registration
                        .requestMatchers("/themes").hasRole("ADMIN") // Protect themes endpoint for ADMIN role
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter to the chain

        return http.build();
    }

    // Define CORS configuration to allow requests from the frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Allow frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allow specific headers
        configuration.setAllowCredentials(true); // Allow credentials like cookies or headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Correct class
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
