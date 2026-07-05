package Input;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerUtil {

    private static final Scanner consoleInput = new Scanner(System.in);

    public static int getIntInput(String choice) {
        System.out.println(choice);
        while (!consoleInput.hasNextInt()) {
            System.out.print("\u001B[31mInvalid choice! Try again: \u001B[0m");
            consoleInput.next();
        }
        int input = consoleInput.nextInt();
        consoleInput.nextLine();
        return input;
    }
    public static BigDecimal amount(String choice) {
        System.out.print(choice);
        while (true) {
            while (!consoleInput.hasNextBigDecimal()) {
                System.out.print("\u001B[31mInvalid amount format! Try again: \u001B[0m");
                consoleInput.next();
            }
            BigDecimal inputAmount = consoleInput.nextBigDecimal();
            consoleInput.nextLine();
            if (inputAmount.compareTo(BigDecimal.ZERO) > 0) {
                return inputAmount;
            }
            System.out.println("\u001B[31mAmount must be greater than zero! Try again: \u001B[0m");
        }
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
            try { Thread.sleep(1000); } catch (InterruptedException e) {

            }
            System.out.print("\r\u001b[2K"); // Clear the error line
        }
    }
    public static String inputPassword(String msg) {
        while (true) {
            System.out.print(msg);
            String input = consoleInput.nextLine();

            if (!input.trim().isEmpty() && input.length() == 8) {
                return input; // Return valid input immediately
            }
            // Show error if empty
            System.out.print("\u001B[31m Password must be of 8 characters!\u001B[0m");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {

            }
            System.out.print("\r\u001b[2K"); // Clear the error line
        }
    }
    public static boolean proceedOrCancel() {
        String input = consoleInput.nextLine().trim();
        return "1".equals(input);
    }
}
