package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.persistence.entity.WordImage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PokemonService {
    private final RestTemplate restTemplate;

    private static final Long POKEMON_THEME_ID = 1L; // Static ID for "Pokemon" theme

    public List<WordImage> getPokemonData(int limit) {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon?limit=" + limit;
        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        List<WordImage> wordImages = new ArrayList<>();
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody().get("results");

        for (Map<String, Object> result : results) {
            String name = (String) result.get("name");
            ResponseEntity<Map> detailsResponse = restTemplate.getForEntity((String) result.get("url"), Map.class);
            String imageUrl = (String) ((Map<String, Object>) detailsResponse.getBody().get("sprites")).get("front_default");

            wordImages.add(new WordImage(name, imageUrl));
        }
        return wordImages;
    }

    public Long getPokemonThemeId() {
        return POKEMON_THEME_ID;
    }
}
