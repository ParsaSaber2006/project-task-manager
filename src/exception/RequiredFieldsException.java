package exception;

import model.Model;
import model.Project;
import model.Task;

public class RequiredFieldsException extends Exception {
    public RequiredFieldsException(String message) {
        super(message);
    }
    public static <E extends Model> void checkRequiredFields(E element) throws RequiredFieldsException {
        if(element instanceof Task) {
            if(element.name.isBlank() || ((Task) element).description.isBlank()) {
                throw new RequiredFieldsException("name and description cannot be empty");
            }
        }
        if(element instanceof Project) {
            if (element.name.isBlank()) {
                throw new RequiredFieldsException("name cannot be empty");
            }
        }
    }
}
