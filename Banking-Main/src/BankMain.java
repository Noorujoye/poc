import java.util.Scanner;
import Exception.InvalidChoiceException;
import Service.AuthService;

public class BankMain {

    private static final Scanner consoleInput = new Scanner(System.in);
    // menu for public
    private static void showMainCard() {
        System.out.println("\n1. Register\n2. Login\n3. Exit");
    }

    // User Menu after login
    private static void userLoginCard() {
        while (true) {
            System.out.println("\nUSER CARD\n" +
                    "\n1. View Balance" +
                    "\n2. Deposit" +
                    "\n3. Withdraw" +
                    "\n4. Transfer Money" +
                    "\n5. Transaction History" +
                    "\n6. Logout");

            int choice = getIntInput("Choose: ");

            switch (choice) {
                case 1:
                    handleViewBalance();
                    break;
                case 2:
                    handleDeposit();
                    break;
                case 3:
                    handleWithdraw();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    handleTransactionHistory();
                    break;
                case 6:
                    System.out.println("Logged out Successfully");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
    private static void handleWithdraw() {
    }
    private static void handleTransactionHistory() {

    }
    private static void handleTransfer() {

    }

    // asking user to select the task
    private static int getIntInput(String choice) {
        System.out.print(choice);

        while (!BankMain.consoleInput.hasNextInt()) {
            System.out.println("Invalid input. Enter number: ");
            BankMain.consoleInput.next();
        }
            return BankMain.consoleInput.nextInt();
       }

    private static String getStringInput(String msg) {
        System.out.print(msg);
        BankMain.consoleInput.nextLine(); // clears the buffer
        return BankMain.consoleInput.nextLine();
    }

    private static double getDoubleInput(String msg) {
        System.out.println(msg);
        while (!BankMain.consoleInput.hasNextDouble()) {
            System.out.println("Invalid input. Enter amount: ");
            BankMain.consoleInput.nextInt();
        }
        return  BankMain.consoleInput.nextDouble();
        }

    private static boolean isLoginHandler() {
        String username = getStringInput("Enter username: ");
        String password = getStringInput("Enter password: ");

        // now we will check user with details


        return true;
    }

    private static void doRegisterHandler() {
        String name = getStringInput("your name: ");
        String phone = getStringInput("your phone: ");
        String panCard = getStringInput("your email: ");
        String aadhaar = getStringInput("your aadhaar: ");
        String address = getStringInput("your address: ");

        AuthService.register(name , phone ,  panCard,  aadhaar , address);

    }

    private static void handleViewBalance() {
        System.out.println();
    }

    private static void handleDeposit() {
        String accNo = getStringInput("Enter account Number: ");
        double amount = getDoubleInput("Enter amount: ");

    }

    // Starting Point
    public static void main(String[] args) {
        System.out.println("Welcome to Banking Service of India");

        while (true) {
           showMainCard();
           int choice = getIntInput("Enter choice: \n");
           switch (choice) {
               case 1:
                   doRegisterHandler();
                   break;
               case 2:
                   if (isLoginHandler()) {
                       userLoginCard(); // access to login-card to user on successfully logging into account
                   }
                   break;
               case 3:
                   System.out.println("Thank you for using SBI Banking!");
                   return;
               default:
                   System.out.println("Invalid choice!");

           }
        }
    }
}
