package assignment.individualtrack.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordImage {

    @NotBlank(message = "Word cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Word must only contain letters")
    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "image_url", nullable = true) // URL for the image
    private String imageUrl;
}
