package service;

import exception.UniqueException;
import model.Project;
import java.util.ArrayList;

public class ProjectService {

    public static void create(String name) throws UniqueException {
        UniqueException.name(name , null , null);
        Project.create(new Project(name));
    }

    public static Project get(int id) throws Exception {
        return (Project) SearchService.findId(id , 1);
    }

    public static void update(int id, String name) throws Exception {
        Project project = (Project) SearchService.findId(id,1);
        int index = Project.getDB().indexOf(project);
        UniqueException.name(name ,project , null);
        project.name = name;
        Project.update(index , project);
    }

    public static void delete(int index) throws Exception{
        Project project = get(index);
        Project.delete(project);
    }

    public static ArrayList<Project> getProjects() {
        return Project.getDB();
    }
}
