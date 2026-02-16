package ui;

import exception.EmptyListException;
import model.Project;
import service.ProjectService;

import java.util.ArrayList;

public class ProjectMenu extends IOConsole {
    protected static void manageProjects() {
        try {
            showProjects();
            int choose = inputInt("Choose a project");
            projectsMenu(choose);
        }catch (EmptyListException e){
            print(e.getMessage());
            ConsoleMenu.mainMenu();
        }

    }

    public static void projectsMenu(int id) {
        print("---------------------");
        print("what do you want to do?\n[1] Add task \n[2] Show tasks \n[3] Edit \n[4] Delete \n[5] Back to main menu");
        print("---------------------");

        switch (inputInt()) {
            case 1-> TaskMenu.addTask(id);
            case 2-> {
                try {
                    TaskMenu.showTasks(id);
                    int taskId = inputInt("Choose a task");
                    TaskMenu.showTask(taskId);
                }catch (EmptyListException e){
                    print(e.getMessage());
                    projectsMenu(id);
                }
            }
            case 3->editProject(id);
            case 4->deleteProject(id);
            case 5->ConsoleMenu.mainMenu();
            default -> {
                print("Invalid choice! Try again:");
                manageProjects();
            }
        }
    }

    protected static void newProject() {
        input.nextLine();
        String projectName = inputLine("Enter project name ");
        try {
            ProjectService.create(projectName);
            print("New project created");
        }catch (Exception e){
            print(e.getMessage());
            print("try again");
        }finally {
            ConsoleMenu.mainMenu();
        }
    }

    protected static void showProjects() throws EmptyListException {
        ArrayList<Project> projects = ProjectService.getProjects();
        EmptyListException.checkProject(projects);
        print("---------------------");

        for (Project project : projects) {
            print("[" + (project.id) + "] " +project.name);
        }
        print("---------------------");
    }

    protected static void editProject(int index) {
        input.nextLine();
        String projectName = inputLine("Enter project name");
        try {
            ProjectService.update(index,projectName);
        }catch (Exception e){
            print("Invalid choice! Try again:");
            ConsoleMenu.mainMenu();
            return;
        }
        print("Edited project");
        ConsoleMenu.mainMenu();
    }

    protected static void deleteProject(int index) {
        try {
            ProjectService.delete(index);
            print("Deleted project");
            ConsoleMenu.mainMenu();
        }
        catch (Exception e){
            print(e.getMessage());
            projectsMenu(index);
        }
    }
}
