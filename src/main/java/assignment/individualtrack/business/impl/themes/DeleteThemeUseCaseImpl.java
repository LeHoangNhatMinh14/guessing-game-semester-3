package assignment.individualtrack.business.impl.themes;

import assignment.individualtrack.business.exception.PlayerNotFoundException;
import assignment.individualtrack.business.exception.ThemeNotFoundException;
import assignment.individualtrack.business.intefaces.DeleteThemeUseCase;
import assignment.individualtrack.persistence.ThemeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteThemeUseCaseImpl implements DeleteThemeUseCase {

    private final ThemeRepo themeRepo;

    @Override
    public void deleteTheme(Long id) {
        if (id == null) {
            throw new ThemeNotFoundException("Theme ID cannot be null");
        }

        // Check if the player exists in the repository
        if (!themeRepo.existsById(id)) {
            throw new PlayerNotFoundException("Player with ID " + id + " does not exist");
        }

        // If player exists, delete
        themeRepo.deleteById(id);
    }
}
