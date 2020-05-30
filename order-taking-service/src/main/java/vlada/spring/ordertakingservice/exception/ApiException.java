package vlada.spring.ordertakingservice.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
