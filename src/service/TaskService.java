package service;

import exception.DeadLineException;
import exception.UniqueException;
import model.Priority;
import model.Project;
import model.Status;
import model.Task;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskService {

    public static void create(int projectId, String name, String description, int priorityNum, String deadLine) throws Exception {
        Priority priority = Priority.values()[priorityNum - 1];
        LocalDateTime deadLineDate;
        Project project = (Project) SearchService.findId(projectId , 1);
        DeadLineException.checkDeadLine(deadLine);
        deadLineDate = LocalDateTime.parse(deadLine , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Task.addTask(new Task(project, name, description, priority, deadLineDate));
    }



    public static void update(int id, String name, String description, int priorityNum, String deadLine) throws Exception {
        Priority priority = Priority.values()[priorityNum - 1];
        Task oldTask = (Task) SearchService.findId(id , 2);


        LocalDateTime deadLineDate;

        UniqueException.name(name , oldTask , oldTask.belongsTo);
        DeadLineException.checkDeadLine(deadLine);
        deadLineDate = LocalDateTime.parse(deadLine , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Task newTask = new Task(oldTask.belongsTo , name.isBlank()?oldTask.name:name, description.isBlank()?oldTask.description:description, priority, deadLineDate);
        Task.update(newTask , oldTask);
    }

    public static void delete(int id) throws Exception {
        Task task = (Task) SearchService.findId(id , 2);
        Task.remove(task);
    }

    public static void changeStatus(int id, Status status) throws Exception {
        Task task = (Task) SearchService.findId(id , 2);
        task.changeStatus(status);
    }

    public static ArrayList<Task> getTasks(){
        for (Task task : Task.getDB() ){
            if(task.getStatus() != Status.TODO)
                continue;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String deadlineStr = task.deadLine.format(formatter);
                DeadLineException.checkDeadLine(deadlineStr);
            }catch (DeadLineException e){
                task.changeStatus(Status.TIMED_OUT);
            }
        }
        return Task.getDB();
    }

    public static String printDeadLine(String deadLine) {
        LocalDateTime DeadLine = LocalDateTime.parse(deadLine);
        return DeadLine.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
