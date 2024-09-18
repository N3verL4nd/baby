package cn.lchospital.baby.exception;

public class JsonDeserializedException extends RuntimeException {

    public JsonDeserializedException(String message) {
        super(message);
    }

    public JsonDeserializedException(Throwable cause) {
        super(cause);
    }

    public JsonDeserializedException(String message, Throwable cause) {
        super(message, cause);
    }

}