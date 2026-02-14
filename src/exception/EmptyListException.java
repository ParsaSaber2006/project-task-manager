package exception;

import model.Project;
import model.Task;

import java.util.ArrayList;

public class EmptyListException extends Exception {
    public EmptyListException() {
        super("List is Empty");
    }

    public static void checkProject(ArrayList<Project> list) throws EmptyListException {
        if (list.isEmpty()){
            throw new EmptyListException();
        }
    }

    public static void checkTask(ArrayList<Task> list) throws EmptyListException {
        if (list.isEmpty()){
            throw new EmptyListException();
        }
    }
}
