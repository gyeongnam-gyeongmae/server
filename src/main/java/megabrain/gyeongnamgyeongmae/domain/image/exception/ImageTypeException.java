package megabrain.gyeongnamgyeongmae.domain.image.exception;

public class ImageTypeException extends RuntimeException {
    public ImageTypeException() {}

    public ImageTypeException(String message) {
        super(message);
    }

    public ImageTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
