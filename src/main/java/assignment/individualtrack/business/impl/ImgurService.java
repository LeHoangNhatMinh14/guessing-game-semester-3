package assignment.individualtrack.business.impl;

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
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImgurService {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final String CLIENT_ID = "fe633d47d0ce304"; // Replace with your Imgur Client ID
    private static final Logger logger = LoggerFactory.getLogger(ImgurService.class);

    public String uploadImage(byte[] imageBytes) throws IOException {
        if (imageBytes == null || imageBytes.length == 0) {
            throw new IllegalArgumentException("Image bytes cannot be null or empty.");
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadRequest = new HttpPost(IMGUR_UPLOAD_URL);
            uploadRequest.setHeader("Authorization", "Client-ID " + CLIENT_ID);

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
                    logger.error("Imgur upload failed with status code: {}", statusCode);
                    throw new IOException("Imgur upload failed with status code: " + statusCode);
                }
            }
        }
    }

    private String parseImageUrl(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            return rootNode.path("data").path("link").asText();
        } catch (Exception e) {
            logger.error("Failed to parse Imgur response JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to parse Imgur response JSON.", e);
        }
    }
}
