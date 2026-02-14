package service;

import model.Project;
import model.Task;
import ui.ConsoleMenu;

import java.util.*;

public class FilterService {
    private static ArrayList<Task> One_filterTask(String filter , ArrayList<Task> tasks) {
        filter = filter.trim();
        filter = filter.toLowerCase();
        switch (filter){
            case "name"->{
                Collections.sort(tasks , Comparator.comparing((Task t) -> t.name));
                ConsoleMenu.print("name filter completed");
            }
            case "status"->{
                Collections.sort(tasks, Comparator.comparing((Task t) -> t.getStatus().getValue()));
                ConsoleMenu.print("status filter completed");
            }
            case "deadline"->{
                Collections.sort(tasks, Comparator.comparing((Task t) -> t.deadLine));
                ConsoleMenu.print("deadline filter completed");
            }
            case "priority"->{
                Collections.sort(tasks , Comparator.comparing((Task t) -> t.priority));
                ConsoleMenu.print("priority filter completed");
            }
            case "project belongs"->{
                Collections.sort(tasks, Comparator.comparing((Task t) -> t.belongsTo.name));
                ConsoleMenu.print("belongs filter completed");
            }
            default -> ConsoleMenu.print(filter + " is not recognized");
        }
        return tasks;
    }

    public static ArrayList<Task> filterTask(String filters , ArrayList<Task> tasks , Project belongsTo) {
        ArrayList<Task> Tasks = new ArrayList<>(tasks);
        if(belongsTo != null) {
            Tasks.removeIf((Task t) -> t.belongsTo != belongsTo);
        }

        filters = filters.toLowerCase();
        String[] arr = filters.split(",");
        arr = Arrays.copyOfRange(arr , 1 , arr.length);

        for (String filter : arr) {
            One_filterTask(filter, Tasks);
        }
        Collections.reverse(Tasks);
        return Tasks;

    }

}
