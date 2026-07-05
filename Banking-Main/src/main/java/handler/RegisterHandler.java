package handler;


import dto.RegistrationRequest;
import exception.BankingException;
import model.Customer;
import service.CustomerService;
import util.Validator;

import static Input.ScannerUtil.*;
import static model.enums.KycStatus.VERIFIED;
import static util.Validator.*;

public class RegisterHandler {

    private final CustomerService customerService;
    private final LoginHandler loginHandler;

    public  RegisterHandler(CustomerService customerService, LoginHandler loginHandler) {
        this.customerService = customerService;
        this.loginHandler = loginHandler;
    }

    public void registerCard() {
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
        System.out.println("""
               \u001B[30mCreate your username and password to login into your account\u001B[0m
               * Password should must be length of 8 characters
               """
        );
        final String username = getStringInput("username: ");
        final String password = inputPassword("password: ");

        final Customer customer = new Customer(firstName + " " + lastName, phone, panCard, aadhaar, address, VERIFIED);

        RegistrationRequest request = new RegistrationRequest(
                customer,
                username,
                password
        );

        String accountNumber;
        try {
            accountNumber = customerService.registerCustomer(request);
        } catch (BankingException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            return;
        }
        System.out.println("+--------------------------------------------------+");
        System.out.println("+        CUSTOMER REGISTERED SUCCESSFULLY!         +");
        System.out.println("+                                                  +");
        System.out.printf( "+      Your Account Number: %-22s +\n", accountNumber);
        System.out.println("+--------------------------------------------------+");

        System.out.println("""
                Want to login
                1.To proceed, Press 1
                2.To return to home page, press anyNumber"""
        );
        int nextStep = getIntInput("choice: ");

        if (nextStep == 1) {
            loginHandler.loginAsUser();
        } else {
            System.out.println("Redirecting to Home page...");
        }
    }
}
