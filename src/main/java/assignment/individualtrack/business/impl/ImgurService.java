package assignment.individualtrack.business.impl;

import assignment.individualtrack.business.exception.ImgurUploadException;
import assignment.individualtrack.business.intefaces.ImgurClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImgurService implements ImgurClient {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final Logger logger = LoggerFactory.getLogger(ImgurService.class);

    @Value("${imgur.client-id}")
    private String clientId;

    @Override
    public String upload(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            throw new IllegalArgumentException("Image bytes cannot be null or empty.");
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadRequest = new HttpPost(IMGUR_UPLOAD_URL);
            uploadRequest.setHeader("Authorization", "Client-ID " + clientId);

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("image", imageBytes, ContentType.APPLICATION_OCTET_STREAM, "image.jpg")
                    .build();
            uploadRequest.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(uploadRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    String jsonResponse = new String(response.getEntity().getContent().readAllBytes());
                    return parseImageUrl(jsonResponse);
                } else {
                    logger.error("Imgur upload failed: Status Code = {}", statusCode);
                    throw new ImgurUploadException("Failed to upload image to Imgur. Status code: " + statusCode);
                }
            }
        } catch (IOException e) {
            // Add contextual information and log the error
            String errorMessage = "IOException occurred during Imgur upload for image bytes of size: " + imageBytes.length;
            logger.error(errorMessage, e);
            throw new ImgurUploadException(errorMessage, e); // Rethrow with context
        }
    }

    private String parseImageUrl(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            return rootNode.path("data").path("link").asText("");
        } catch (Exception e) {
            logger.error("Failed to parse Imgur response JSON: {}", e.getMessage());
            throw new ImgurUploadException("Failed to parse Imgur response JSON.", e);
        }
    }
}
