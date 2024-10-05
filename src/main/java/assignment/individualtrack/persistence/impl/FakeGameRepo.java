package assignment.individualtrack.persistence.impl;

import org.springframework.stereotype.Repository;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<GameEntity> findTop20ByPlayerIdOrderByIdDesc(long playerId) {
        // Filter games by playerId, sort by id in descending order, and limit to 20 results
        return this.games.stream()
                .filter(game -> game.getPlayerId() == playerId) // Filter by playerId
                .sorted(Comparator.comparingLong(GameEntity::getId).reversed()) // Sort by id descending
                .limit(20) // Limit to top 20
                .collect(Collectors.toList());
    }
}
