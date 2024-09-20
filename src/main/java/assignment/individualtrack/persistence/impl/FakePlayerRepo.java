package assignment.individualtrack.persistence.impl;

import org.springframework.stereotype.Repository;
import assignment.individualtrack.persistence.PlayerRepo;
import assignment.individualtrack.persistence.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakePlayerRepo implements PlayerRepo {
private static long NEXT_ID = 1;
private final List<PlayerEntity> players = new ArrayList<>();
    @Override
    public boolean existsbyName(String name) {
        return this.players
                .stream()
                .anyMatch(studentEntity -> studentEntity.getName().equals(name));
    }

    @Override
    public PlayerEntity save(PlayerEntity player) {
        if (player.getId() == null) {
            player.setId(NEXT_ID);
            NEXT_ID++;
            this.players.add(player);
        }
        return player;
    }

    @Override
    public void deletebyID(long id) {
        this.players.removeIf(studentEntity -> studentEntity.getId().equals(id));
    }

    @Override
    public Optional<PlayerEntity> findbyID(Long id) {
        return this.players.stream()
                .filter(studentEntity -> studentEntity.getId().equals(id))
                .findFirst();
    }

    public Optional<PlayerEntity> getbyID(Long id) {
        return this.players.stream()
                .filter(PlayerEntity -> PlayerEntity.getId().equals(id))
                .findFirst();
    }
}
