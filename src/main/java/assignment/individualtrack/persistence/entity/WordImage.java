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

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "Invalid URL format")
    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    // Convenience constructor for creating instances without an image URL
    public WordImage(String word) {
        this.word = word;
        this.imageUrl = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordImage wordImage = (WordImage) o;
        return word.equalsIgnoreCase(wordImage.word); // Case-insensitive comparison
    }

    @Override
    public int hashCode() {
        return word.toLowerCase().hashCode(); // Case-insensitive hashCode
    }
}