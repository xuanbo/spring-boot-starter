package tk.fishfish.json;

/**
 * json exception
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class JsonException extends RuntimeException {

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

}
