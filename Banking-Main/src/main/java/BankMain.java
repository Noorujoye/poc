import static Input.ScannerUtil.getIntInput;
import static handler.Home.showMainCard;
import static util.AppFactory.loginHandler;
import static util.AppFactory.registerHandler;

public class BankMain {
    private static void startApplication() {
        System.out.println("Welcome to Bank Service of India");
        while (true) {
            showMainCard();
            try {
                int choice = getIntInput("How we can help you: ");
                switch (choice) {
                    case 1 ->
                                    registerHandler()
                                            .registerCard();
                    case 2 ->
                                    loginHandler()
                                            .loginAsUser();
                    case 3 ->       loginHandler()
                                            .loginAsAdmin();

                    case 4 -> {
                        System.out.println("Thank you for using India Bank!");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong! please try again...");
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            startApplication();
        } catch (Exception e) {
            System.out.println("Unexpected system error");
            e.printStackTrace();
        }
    }
}
