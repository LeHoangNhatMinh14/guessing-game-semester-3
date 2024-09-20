package assignment.individualtrack.business.impl;

import assignment.individualtrack.domain.Player;
import assignment.individualtrack.persistence.entity.PlayerEntity;

public class PlayerConverter {
    private PlayerConverter() {
    }
        public static Player convertToDomain(PlayerEntity playerEntity) {
            return Player.builder()
                    .id(playerEntity.getId())
                    .name(playerEntity.getName())
                    .highScore(playerEntity.getHighscore())
                    .build();
        }

        public static PlayerEntity convertToEntity(Player player) {
            return PlayerEntity.builder()
                    .id(player.getId())
                    .name(player.getName())
                    .highscore(player.getHighScore())
                    .build();
        }
    }
