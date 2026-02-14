package exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super("This task does not exist!");
    }
}
