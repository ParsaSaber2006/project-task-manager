package ui;

import java.util.Scanner;

public class IOConsole {
    public static final Scanner input = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }

    public static int inputInt() {
        System.out.print("Enter your choice: ");
        try {
            return input.nextInt();
        }catch (Exception e){
            print("Invalid input");
            input.next();
            return inputInt();
        }
    }

    public static int inputInt(String message) {
        System.out.print(message+": ");
        try {
            return input.nextInt();
        }catch (Exception e){
            print("Invalid input");
            input.next();
            return inputInt(message);
        }

    }

    public static String inputLine(String message) {
        System.out.print(message + ": ");
        return input.nextLine();
    }


}
