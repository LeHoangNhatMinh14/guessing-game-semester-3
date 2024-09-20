package assignment.individualtrack.persistence;

import assignment.individualtrack.persistence.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepo {
    boolean existsbyName(String name);
    PlayerEntity save(PlayerEntity player);
    void deletebyID(long id);
    Optional<PlayerEntity> findbyID(Long id);
}
