package assignment.individualtrack.persistence.impl;

import org.springframework.stereotype.Repository;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeGameRepo implements GameRepo {
private static long NEXT_ID = 1;
private final List<GameEntity> games;
public FakeGameRepo() { this.games = new ArrayList<GameEntity>(); }

    @Override
    public Optional<GameEntity> findbyID(long id) {
        return this.games.stream()
                .filter(studentEntity -> studentEntity.getId() == id)
                .findFirst();
    }

    @Override
    public GameEntity save(GameEntity game) {
        game.setId(NEXT_ID);
        NEXT_ID++;
        this.games.add(game);
        return game;
    }
}
