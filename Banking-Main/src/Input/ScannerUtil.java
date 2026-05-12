package Input;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerUtil {

    private static final Scanner consoleInput = new Scanner(System.in);

    // asking user to select the task
    public static int getIntInput(String choice) {
        System.out.println(choice);
        while (!consoleInput.hasNextInt()) {
            consoleInput.next();
        }
        int input = consoleInput.nextInt();
        consoleInput.nextLine();
        return input;
    }
    public static BigDecimal amount(String choice) {
        System.out.print(choice);
        while (!consoleInput.hasNextBigDecimal()) {
            consoleInput.next();
        }
        BigDecimal inputAmount = consoleInput.nextBigDecimal();
        consoleInput.nextLine();
        return inputAmount;
    }

    public static String getStringInput(String msg) {
        while (true) {
            System.out.print(msg);
            String input = consoleInput.nextLine();

            if (!input.trim().isEmpty()) {
                return input; // Return valid input immediately
            }

            // Show error if empty
            System.out.print("\u001B[31m Input cannot be empty!\u001B[0m");
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            System.out.print("\r\u001b[2K"); // Clear the error line
        }
    }
    public static boolean proceedOrCancel() {
        String input = consoleInput.nextLine();
        if (input.trim().equals("1")) {
            return true;
        }
        return false;
    }
}
