package assignment.individualtrack.persistence.entity;

import assignment.individualtrack.domain.Game.GameStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "game")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "time", nullable = false)
    private int time;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GameStatus status;

    @Column(name = "correct_guesses", nullable = false)
    private int correctGuesses;

    @Column(name = "wrong_guesses", nullable = false)
    private int wrongGuesses;

    // Add a reference to the ThemeEntity
    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private ThemeEntity theme;

    // Add a playedAt field to track when the game was played
    @Column(name = "played_at", nullable = false)
    private LocalDateTime playedAt;
}
