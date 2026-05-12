import static Input.ScannerUtil.*;
import static handler.RegisterHandler.registerCard;
import static ui.Home.showMainCard;
import static util.AppFactory.loginHandler;

public class BankMain {
    public static void main(String[] args) {
        System.out.println("Welcome to Bank Service of India");
        while (true) {
            showMainCard();
            try {
                int choice = getIntInput("How we can help you: ");
                switch (choice) {
                    case 1:
                        registerCard();
                        break;
                    case 2:
                        loginHandler().login();
                        break;
                    case 3:
                        System.out.println("Thank you for using India Bank!");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong! please try again...");
                e.printStackTrace();
            }
        }
    }
}
