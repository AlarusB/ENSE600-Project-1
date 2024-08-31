
import java.util.Scanner;

/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static int chooseAction(int min, int max) {
        int choice = 0;
        boolean isValid = false;
        do {
            System.out.print("Choose an action: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.println("Invalid input. Enter an action between range: (" + min + "-" + max + ")");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid action.");
            }
        } while (!isValid);
        return choice;
    }
}
