package TaskFlow_api.TaskFlow_api.exception;

public class TaskAlreadyMadeException extends RuntimeException {
    public TaskAlreadyMadeException(String message) {
        super(message);
    }
}
