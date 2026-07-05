package util;

import java.util.function.Predicate;

import static Input.ScannerUtil.getStringInput;

public class Validator {
    // Return boolean so calling method can decide whether to loop or proceed
    public static boolean isValidName(String name) {
        return name.matches("^[a-zA-Z\\s]{2,30}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^[6-9]\\d{9}$");
    }

    public static boolean isValidAadhaar(String aadhaar) {
        return aadhaar.matches("^\\d{12}$");
    }

    public static boolean isValidPan(String pan) {
        // Real PAN: 5 Letters, 4 Digits, 1 Letter
        return pan.toUpperCase().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    }
    public static boolean isValidAddress(String address) {
        return address.matches("^[a-zA-Z0-9\\s,\\-\\.]{10,}$");
    }

    public static String getValidateField(String labelStatement, Predicate<String> condition) {
       while (true) {
           String input = getStringInput(labelStatement);
           if (condition.test(input)) {
               return input;
           }
           System.out.println("\u001B[31m Invalid format! Try again.\u001B[0m");
           try {
               Thread.sleep(1500);
           } catch (Exception e) {
               System.out.println("\r\u001b[2K");
           }
       }
    }
}
