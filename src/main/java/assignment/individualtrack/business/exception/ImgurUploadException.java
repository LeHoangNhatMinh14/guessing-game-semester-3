package assignment.individualtrack.business.exception;

public class ImgurUploadException extends RuntimeException {
    public ImgurUploadException(String message) {
        super(message);
    }

    public ImgurUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
