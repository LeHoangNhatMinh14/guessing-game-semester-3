package assignment.individualtrack.business.intefaces;

public interface ImgurClient {
    /**
     * Uploads an image to Imgur and returns the URL of the uploaded image.
     *
     * @param imageBytes the image in byte array format.
     * @return the URL of the uploaded image.
     */
    String upload(byte[] imageBytes);
}
