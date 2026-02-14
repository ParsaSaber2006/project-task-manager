package ui;

import exception.EmptyListException;
import exception.UniqueException;
import model.Project;
import model.Status;
import model.Task;
import service.FilterService;
import service.SearchService;
import service.TaskService;
import java.util.ArrayList;

public class TaskMenu extends IOConsole {

    protected static void addTask(int projectNum) {
        input.nextLine();
        String taskName = inputLine("Enter the name of the task ");
        try {
            UniqueException.name(taskName , projectNum);
        }catch (Exception e){
            print(e.getMessage());
            print("try again");
            addTask(projectNum);
        }
        String description = inputLine("Enter the description of the task");
        int priority = inputInt("Enter the priority of the task: \n[1] Low\n[2] Medium\n[3] High");
        String deadLine = getDeadLine();
        try {
            TaskService.create(projectNum,taskName,description,priority,deadLine);
        }catch (Exception e) {
            print(e.getMessage());
            print("Try again:");
            addTask(projectNum);
        }
        print("New task created");
        ProjectMenu.manageProjects();

    }

    protected static void showTasks(int projectId) throws EmptyListException {
        Project project;
        ArrayList<Task> tasks;
        try {
            project = (Project) SearchService.findId(projectId , 1);
            ArrayList<Task> allTasks = TaskService.getTasks();
            tasks = FilterService.filterTask("" , allTasks , project);
        }catch (Exception e) {
            print(e.getMessage());
            return;
        }
        EmptyListException.checkTask(tasks);
        print("---------------------");
        for (Task task : tasks)
            printTask(task);
        print("---------------------");
    }

    protected static void showTask(int taskId) {
        Task task;
        try {
            task = (Task) SearchService.findId(taskId , 2);
        }
        catch (Exception e) {
            print(e.getMessage());
            print("Unknown Error\nTry again:");
            ProjectMenu.manageProjects();
            return;
        }
        String status = task.getStatus().name().toLowerCase();
        print("---------------------"+
                "\nTask: " + task.name + "\nDescription:\n"+task.description + "\nstatus: " + status +
                "\n---------------------"
        );
        boolean isTodo = task.getStatus() == Status.TODO;
        print("---------------------");
        print("what do you want to do?");
        print(
                isTodo?"[1] edit\n[2] delete\n[3] complete task\n[0] Back to main menu"
                        :"[1] edit\n[2] delete\n[0] Back to main menu"
        );
        print("---------------------");
        switch (inputInt()) {
            case 0: {
                ConsoleMenu.mainMenu();
                break;
            }
            case 1: {
                editTask(taskId);
                break;
            }
            case 2: {
                deleteTask(taskId);
                break;
            }
            case 3:{
                if(isTodo) {
                    try {
                        TaskService.changeStatus(taskId, Status.COMPLETED);
                    }catch (Exception e){
                        print("task not found");
                        ProjectMenu.manageProjects();
                        return;
                    }
                    print("Task completed");
                    ProjectMenu.manageProjects();
                    break;
                }
            }
            default:{
                print("Invalid choice! Try again:");
                showTask(taskId);
            }
        }
    }

    protected static void editTask(int taskId) {

        input.nextLine();
        String name = inputLine("Enter the name of the task ");
        String description = inputLine("Enter the description of the task if you want to change");
        int priority = inputInt("Enter the priority of the task: \n[1] Low\n[2] Medium\n[3] High");
        String deadLine = getDeadLine();
        try {
            TaskService.update(taskId, name, description, priority, deadLine);
        }catch (Exception e) {
            print(e.getMessage());
            print("Try again:");
            editTask(taskId);
        }
        print("task edited successfully");
        showTask(taskId);
    }

    protected static void deleteTask(int taskId) {
        try {
            TaskService.delete(taskId);
            print("task deleted successfully");
        }
        catch (Exception e) {
            print(e.getMessage());
            print("Try again:");
        }
        finally {
            ConsoleMenu.mainMenu();
        }
    }

    protected static String getDeadLine() {
        int year = inputInt("Enter the year of the task deadline ");
        int month = inputInt("Enter the month of the task deadline ");
        int day = inputInt("Enter the day of the task deadline ");
        int hour = inputInt("Enter the hour of the task deadline ");
        int minute = inputInt("Enter the minute of the task deadline ");
        String deadLineDate = String.format("%04d-%02d-%02d" , year, month, day);
        String deadLineTime = String.format("%02d:%02d" , hour , minute);
        return String.join(" ", deadLineDate , deadLineTime);
    }

    protected static void showAllTasks() {
        String filter = ConsoleMenu.getFilters();
        ArrayList<Task> tasks = FilterService.filterTask(filter,TaskService.getTasks(),null);
        try {
            EmptyListException.checkTask(tasks);
            for (Task task : tasks){
                printTask(task);
            }
            int taskId = inputInt("Select an task");
            showTask(taskId);
        }catch (EmptyListException e) {
            print(e.getMessage());
            ConsoleMenu.mainMenu();
        }

    }

    protected static void printTask(Task task) {
        String id = "["+ task.id + "] ";
        String name = task.name;
        Project belongsTo = task.belongsTo;
        String deadLine = task.deadLine.toString();
        String priority = task.priority.name().toLowerCase();
        print(id + name + "\tBelongs to: " + belongsTo.name + "\tDeadline: " + TaskService.printDeadLine(deadLine) + "\tPriority: " + priority);
    }

}
