package assignment.individualtrack.persistence.entity;

import assignment.individualtrack.persistence.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "player")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "highscore")
    private Integer highscore;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Use Enum for roles
    @Column(name = "role", nullable = false)
    private Role role; // e.g., USER, ADMIN
}
