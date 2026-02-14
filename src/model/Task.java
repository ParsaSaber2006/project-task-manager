package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Model {
    private static ArrayList<Task> DB = new ArrayList<>();
    private static int primaryKey = 0;

    public Project belongsTo;
    public String description;
    public Priority priority;
    public LocalDateTime deadLine;
    private Status status = Status.TODO;

    public Task(Project project, String name, String description, Priority priority, LocalDateTime deadLine) {
        super(primaryKey + 1 , name);
        this.description = description;
        this.priority = priority;
        this.belongsTo = project;
        this.deadLine = deadLine;
    }

    public static void addTask(Task task) {
        DB.add(task);
        ++primaryKey;
    }

    public static void remove(Task task) {
        DB.remove(task);
    }

    public static void update(Task newtask , Task oldTask) {
        oldTask.name = newtask.name;
        oldTask.description = newtask.description;
        oldTask.priority = newtask.priority;
        oldTask.deadLine = newtask.deadLine;
    }

    public static ArrayList<Task> getDB() {
        return DB;
    }
    public void changeStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
