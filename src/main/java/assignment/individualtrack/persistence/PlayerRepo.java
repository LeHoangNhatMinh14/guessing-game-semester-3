package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlayerRepo extends JpaRepository<PlayerEntity, Long> {
    boolean existsByName(String name);
    Optional<PlayerEntity> findById(Long id);
    @Query("SELECT p FROM PlayerEntity p WHERE p.name = :name")
    Optional<PlayerEntity> findByName(@Param("name") String name);
}
