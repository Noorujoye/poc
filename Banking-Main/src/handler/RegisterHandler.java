package handler;

import auth.Credentials;
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
import static model.enums.KycStatus.VERIFIED;
import static util.Validator.*;

public class RegisterHandler {
    private static final CustomerRepository customerRepository = new CustomerRepositoryDB();
    private static final AccountRepository accountRepository = new AccountRepositoryDB();
    private static final CredentialsRepository credentialsRepository = new CredentialRepositoryDB();
    private static LoginHandler authService;

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
                To Open your Account, please provide the following details:
                """
        );
        String firstName = getValidateField("(*)your firstName: ", input -> isValidName(input));
        String lastName = getValidateField("(*)your lastName: ", input -> isValidName(input));
        String phone = getValidateField("(*)your phone: ", Validator::isValidPhone);
        String panCard = getValidateField("(*)your panCard ", input -> isValidPan(input));
        String aadhaar = getValidateField("(*)your aadhaar: ", input -> isValidAadhaar(input));
        String address = getValidateField("(*)your address: ", input -> isValidAddress(input));
        Enum kycStatus;

        final Customer customer = new Customer(firstName + " " + lastName, phone, panCard, aadhaar, address, VERIFIED);
        CustomerService registrationService = new CustomerService(accountRepository, customerRepository, credentialsRepository);
        String accNo = null;
        try {
            accNo = registrationService.createInitialCustomerAndAccount(customer);
            if (accNo == null) return;
        } catch (RuntimeException e) {
            System.out.println("\u001B[31m ERROR: " + e.getMessage() + "\u001B[0m");
            return;
        }
        System.out.println("+--------------------------------------------------+");
        System.out.println("+        CUSTOMER REGISTERED SUCCESSFULLY!         +");
        System.out.println("+                                                  +");
        System.out.printf( "+      Your Account Number: %-22s +\n", accNo        );
        System.out.println("+--------------------------------------------------+");

// Account number wali line ko align karne ke liye format string use karenge
        System.out.printf("+      Your Account Number: %-22s +\n", accNo);
        System.out.println("""
                Create your username and password to login into your account
                """);
        String username = getStringInput("username: ");
        String password = getStringInput("password: ");
        registrationService.saveCredentials(customer.getCustomerId(), username, password);
        System.out.println("""
                Want to login
                1.To proceed, Press 1
                2.To return to home page, press anyNumber"""
        );
        int nextStep = getIntInput("choice: ");
        if (nextStep == 1) {
            util.AppFactory.loginHandler().login();
        } else {
            System.out.println("Redirecting to Home page...");
        }
    }
}
