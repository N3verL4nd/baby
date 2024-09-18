package cn.lchospital.baby.exception;

public class JsonSerializedException extends RuntimeException {

    public JsonSerializedException(String message) {
        super(message);
    }

    public JsonSerializedException(Throwable cause) {
        super(cause);
    }

    public JsonSerializedException(String message, Throwable cause) {
        super(message, cause);
    }

}