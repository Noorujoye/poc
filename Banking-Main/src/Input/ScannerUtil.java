package Input;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerUtil {

    private static final Scanner consoleInput = new Scanner(System.in);

    // asking user to select the task
    public static int getIntInput(String choice) {
        System.out.print(choice);
        while (!consoleInput.hasNextInt()) {
            consoleInput.next();
        }
        return consoleInput.nextInt();
    }
    public static BigDecimal amount(String choice) {
        System.out.print(choice);
        while (!consoleInput.hasNextInt()) {
            consoleInput.next();
        }
        return consoleInput.nextBigDecimal();
    }

    public static String getStringInput(String msg) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(msg);
            input = consoleInput.nextLine();

            if (input.trim().isEmpty()) {
                System.out.print("\u001B[31m Input cannot be empty!\u001B[0m");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.print("\r\u001b[2K");
        }
        return input;
    }
    public static boolean proceedOrCancel() {
        consoleInput.nextLine();
        String input = consoleInput.nextLine();
        if (input.trim().equals("1")) {
            return true;
        }
        return false;
    }
}
