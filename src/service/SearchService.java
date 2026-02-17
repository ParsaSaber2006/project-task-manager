package service;

import exception.ProjectNotFoundException;
import exception.TaskNotFoundException;
import model.Model;
import java.util.ArrayList;

public class SearchService {
    public static ArrayList<? extends Model> findName(String name , int type) throws Exception {
        ArrayList<? extends Model> list;
        if (type == 1) {
            list = new ArrayList<>(ProjectService.getProjects());
        }else if (type == 2) {
            list = new ArrayList<>(TaskService.getTasks());
        }else {
            throw new Exception("Unknown type.");
        }
        String Name = name.toLowerCase();
        list.removeIf((Model item) -> !(item.name.toLowerCase().contains(Name)));

        if (list.isEmpty()) {
            if (type == 1) {
                throw new ProjectNotFoundException();
            }
            else {
                throw new TaskNotFoundException();
            }
        }
            return list;

    }

    public static Model findId(int id , int type) throws Exception {
        ArrayList<Model> list;
        if (type == 1) {
            list = new ArrayList<>(ProjectService.getProjects());
        } else if (type == 2) {
            list = new ArrayList<>(TaskService.getTasks());
        } else {
            throw new Exception("Unknown type");
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            int currentId = list.get(middle).id;

            if(currentId == id){
                return list.get(middle);
            }
            if(currentId < id){
                left = middle + 1;
            }
            else {
                right = middle - 1;
            }
        }
        if (type == 1) {
            throw new ProjectNotFoundException();
        }else {
            throw new TaskNotFoundException();
        }
    }

}
