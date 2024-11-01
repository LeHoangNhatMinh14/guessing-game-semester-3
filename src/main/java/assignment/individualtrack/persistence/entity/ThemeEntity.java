package assignment.individualtrack.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "theme")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "theme_words", joinColumns = @JoinColumn(name = "theme_id"))
    @Column(name = "word")
    private List<String> words;
}