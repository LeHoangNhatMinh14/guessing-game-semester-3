package assignment.individualtrack;

import assignment.individualtrack.business.exception.ImgurUploadException;
import assignment.individualtrack.business.impl.ImgurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImgurServiceIntegrationTest {

    @Autowired
    private ImgurService imgurService; // Spring will inject the correct instance with `client-id`

    @Test
    void upload_successfulUpload_shouldReturnImageUrl() {
        // Arrange
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(Paths.get("src/test/resources/test-image.jpg"));
        } catch (IOException e) {
            fail("Failed to read the test image file: " + e.getMessage());
            return;
        }

        // Act
        try {
            String result = imgurService.upload(imageBytes);

            // Assert
            assertNotNull(result, "Image URL should not be null");
            assertTrue(result.startsWith("https://"), "Image URL should start with a valid protocol");
            System.out.println("Imgur link: " + result); // Prints the link for verification
        } catch (ImgurUploadException e) {
            fail("Unexpected failure during image upload: " + e.getMessage());
        }
    }

    @Test
    void upload_emptyBytes_shouldThrowIllegalArgumentException() {
        // Arrange
        byte[] emptyBytes = new byte[0];

        // Act & Assert
        ImgurUploadException exception = assertThrows(
                ImgurUploadException.class,
                () -> imgurService.upload(emptyBytes)
        );
        assertEquals("Image bytes cannot be null or empty.", exception.getMessage());
    }

    @Test
    void upload_nullBytes_shouldThrowIllegalArgumentException() {
        // Act & Assert
        ImgurUploadException exception = assertThrows(
                ImgurUploadException.class,
                () -> imgurService.upload(null)
        );
        assertEquals("Image bytes cannot be null or empty.", exception.getMessage());
    }
}
