package exception;

import model.Model;
import model.Project;
import model.Task;
import service.ProjectService;
import service.SearchService;
import java.util.ArrayList;

public class UniqueException extends Exception {
    public UniqueException(String message) {
        super(message);
    }

    public static void name(String name, Model old , Project project) throws UniqueException {
        ArrayList<Model> list;
        try {
            int type = project == null ? 1 : 2;
            list = SearchService.findName(name , type);

            if (type == 2){
                list.removeIf((Model model)-> ((Task)model).belongsTo != project);
            }
            String Name = name.toLowerCase();
            list.removeIf((Model item) -> !(item.name.toLowerCase().equals(Name)) || item == old);
            if (list.isEmpty()) {
                return;
            }
        }catch (Exception e){
            return;
        }

        throw new UniqueException("Name : " + name + " already exist");

    }

    public static void name(String name , int projectId) throws Exception {
        Project project = ProjectService.get(projectId);
        name(name , null ,project);
    }
}
