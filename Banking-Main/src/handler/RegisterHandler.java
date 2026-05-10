package handler;

import model.Customer;
import repository.CredentialsRepository;
import repository.CustomerRepository;
import repository.AccountRepository;
import repository.impl.AccountRepositoryDB;
import repository.impl.CredentialRepositoryDB;
import repository.impl.CustomerRepositoryDB;
import service.CustomerService;
import util.Validator;

import static Input.ScannerUtil.*;
import static ui.Home.showMainCard;
import static util.Validator.*;

public class RegisterHandler {
    private static final CustomerRepository customerRepository = new CustomerRepositoryDB();
    private static final AccountRepository accountRepository = new AccountRepositoryDB();
    private static final CredentialsRepository credentialsRepository = new CredentialRepositoryDB();

    public static void registerCard() {
        System.out.println("""
                Do you really want to register...
                1.To proceed, Press 1
                2.To cancel, press 0"""
        );
        if (!proceedOrCancel()) {
            return;
        }
        System.out.print("""
                Registration....
                To Open your Account, please provide the following details:\n"""
        );
        String firstName = getValidateField("(*)your firstName: " , input -> isValidName(input));
        String lastName  = getValidateField("(*)your lastName: "  , input -> isValidName(input));
        String phone     = getValidateField("(*)your phone: "     , Validator::isValidPhone);
        String panCard   = getValidateField("(*)your panCard "    , input -> isValidPan(input));
        String aadhaar   = getValidateField("(*)your aadhaar: "   , input -> isValidAadhaar(input));
        String address   = getValidateField("(*)your address: "   , input -> isValidAddress(input));

        System.out.println("""
               Create your username and password to login into your account
                """);
        String username = getStringInput("username: ");
        String password = getStringInput("password: ");

        final Customer customer = new Customer(firstName + " " + lastName , phone , panCard , aadhaar , address);
        CustomerService registrationService = new CustomerService(accountRepository , customerRepository , credentialsRepository);
        registrationService.registerCustomer(customer , username  , password);;
        showMainCard();
    }
}
