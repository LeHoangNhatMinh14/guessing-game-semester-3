package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepo extends JpaRepository<PlayerEntity, Integer> {
    boolean existsByName(String name);
    Optional<PlayerEntity> findById(Long id);
    void deleteById(Long id);
}
