package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.domain.Game.Game;
import assignment.individualtrack.persistence.entity.GameEntity;

public class GameConverter {

    private GameConverter() {
        // Private constructor to prevent instantiation
    }

    public static Game convert(GameEntity gameEntity) {
        if (gameEntity == null) {
            return null;
        }

        return Game.builder()
                .id(gameEntity.getId())
                .score(gameEntity.getScore())
                .time(gameEntity.getTime())
                .playerId(gameEntity.getPlayer().getId())
                .status(gameEntity.getStatus())
                .build();
    }

    public static GameEntity convertToEntity(Game game) {
        if (game == null) {
            return null;
        }

        return GameEntity.builder()
                .id(game.getId())
                .score(game.getScore())
                .time(game.getTime())
                .status(game.getStatus())
                // Assuming you have methods to fetch PlayerEntity and ThemeEntity by ID
                .build();
    }
}
