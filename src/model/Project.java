package model;

import java.util.*;

public class Project extends Model {
    private static ArrayList<Project> DB = new ArrayList<>();
    private static int primaryKey = 0;


    public Project(String name) {
        super(primaryKey + 1 , name);
    }

    public static void create(Project project) {
        DB.add(project);
        ++primaryKey;
    }

    public static void delete(Project project) {
        DB.remove(project);
        Task.getDB().removeIf(t -> t.belongsTo == project);
    }

    public static void update(int index , Project newProject) {
        DB.set(index , newProject);
    }

    public static ArrayList<Project> getDB() {
        return DB;
    }
}
