package ui;

import model.Model;
import model.Project;
import model.Task;
import service.SearchService;

import java.util.ArrayList;

public class ConsoleMenu extends IOConsole {

    public static void mainMenu() {
        print("Welcome to the main menu \n[1] new project \n[2] show projects\n[3] show all tasks \n[4] search \n[0] exit program");
        switch (inputInt()) {
            case 0 -> print("Exiting program");
            case 1->ProjectMenu.newProject();

            case 2-> ProjectMenu.manageProjects();

            case 3-> TaskMenu.showAllTasks();

            case 4-> search();

            default -> {
                print("Invalid choice! Try again:");
                mainMenu();
            }
        }
    }

    protected static String getFilters(){
        print("[name] \n[status] \n[deadLine] \n[priority] \n[project belongs]");
        input.nextLine();
        String filter = inputLine("Enter filters(Separate filters with ',') ");
        filter = "," + filter;
        return filter;
    }

    protected static void search(){
        print("find according ...");
        print("[1] id \n[2] name");
        int according = inputInt();
        print("search for ...");
        print("[1] Project \n[2] Task");
        int type = inputInt();
        Model item = null;
        ArrayList<Model> list = new ArrayList<>();

        switch (according){
            case 1->{
                int id = inputInt("Enter ID");
                try {
                    item = SearchService.findId(id, type);
                }catch (Exception e){
                    print(e.getMessage());
                    mainMenu();
                    return;
                }
            }
            case 2->{
                input.nextLine();
                String name = inputLine("Enter Name");
                try {
                    list = SearchService.findName(name , type);
                }catch (Exception e){
                    print(e.getMessage());
                    mainMenu();
                    return;
                }
            }
            default -> {
                print("Invalid choice! Try again:");
                search();
                return;
            }
        }
        if (according == 1){
            if (item instanceof Task Item){
                TaskMenu.printTask(Item);
                TaskMenu.showTask(Item.id);
            }
            if (item instanceof Project Item){
                int id = Item.id;
                String name = Item.name;
                print("---------------------");
                print("["+id+"] "+name);
                print("---------------------");

                ProjectMenu.projectsMenu(id);
            }
            return;
        }
        if (according == 2){
            if(list.getFirst() instanceof Task){
                for (Model model : list){
                    TaskMenu.printTask((Task) model);
                }
                int choose = inputInt();
                TaskMenu.showTask(choose);
            }
            else if (list.getFirst() instanceof Project){
                for (Model model : list){
                    Project project = (Project) model;
                    print("---------------------");
                    print("["+project.id+"] "+project.name);
                    print("---------------------");
                }
                int choose = inputInt();
                ProjectMenu.projectsMenu(choose);
            }
        }
    }

}
