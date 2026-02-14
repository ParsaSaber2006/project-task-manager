package exception;

public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException() {
        super("this project does not exist!");
    }
}
