package assignment.individualtrack.theme;

import assignment.individualtrack.business.impl.themes.PokemonService;
import assignment.individualtrack.persistence.entity.WordImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPokemonData_successful() {
        // Arrange
        int limit = 2;
        String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=" + limit;

        mockMainApiCall(apiUrl);

        mockPokemonDetails("https://pokeapi.co/api/v2/pokemon/25", "https://pikachu-image-url.com");
        mockPokemonDetails("https://pokeapi.co/api/v2/pokemon/1", "https://bulbasaur-image-url.com");

        // Act
        List<WordImage> wordImages = pokemonService.getPokemonData(limit);

        // Assert
        assertEquals(2, wordImages.size());
        assertEquals("pikachu", wordImages.get(0).getWord());
        assertEquals("https://pikachu-image-url.com", wordImages.get(0).getImageUrl());
        assertEquals("bulbasaur", wordImages.get(1).getWord());
        assertEquals("https://bulbasaur-image-url.com", wordImages.get(1).getImageUrl());

        verify(restTemplate, times(1)).getForEntity(apiUrl, Map.class);
        verify(restTemplate, times(1)).getForEntity("https://pokeapi.co/api/v2/pokemon/25", Map.class);
        verify(restTemplate, times(1)).getForEntity("https://pokeapi.co/api/v2/pokemon/1", Map.class);
    }

    @Test
    void getPokemonData_emptyResults() {
        // Arrange
        int limit = 0;
        String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=" + limit;

        // Mocking the response with empty results
        Map<String, Object> emptyResponse = new HashMap<>();
        emptyResponse.put("results", List.of());
        when(restTemplate.getForEntity(apiUrl, Map.class)).thenReturn(ResponseEntity.ok(emptyResponse));

        // Act
        List<WordImage> wordImages = pokemonService.getPokemonData(limit);

        // Assert
        assertTrue(wordImages.isEmpty()); // Ensure no WordImage is returned
        verify(restTemplate, times(1)).getForEntity(apiUrl, Map.class); // Verify the main API call
        verifyNoMoreInteractions(restTemplate); // Ensure no further interactions with RestTemplate
    }

    @Test
    void getPokemonThemeId_returnsStaticId() {
        // Act
        Long themeId = pokemonService.getPokemonThemeId();

        // Assert
        assertEquals(1L, themeId);
    }

    private void mockMainApiCall(String apiUrl) {
        Map<String, Object> pokemon1 = new HashMap<>();
        pokemon1.put("name", "pikachu");
        pokemon1.put("url", "https://pokeapi.co/api/v2/pokemon/25");

        Map<String, Object> pokemon2 = new HashMap<>();
        pokemon2.put("name", "bulbasaur");
        pokemon2.put("url", "https://pokeapi.co/api/v2/pokemon/1");

        Map<String, Object> mainResponse = new HashMap<>();
        mainResponse.put("results", List.of(pokemon1, pokemon2));

        when(restTemplate.getForEntity(apiUrl, Map.class)).thenReturn(ResponseEntity.ok(mainResponse));
    }

    private void mockPokemonDetails(String url, String imageUrl) {
        Map<String, Object> sprites = new HashMap<>();
        sprites.put("front_default", imageUrl);

        Map<String, Object> detailsResponse = new HashMap<>();
        detailsResponse.put("sprites", sprites);

        when(restTemplate.getForEntity(url, Map.class)).thenReturn(ResponseEntity.ok(detailsResponse));
    }
}
