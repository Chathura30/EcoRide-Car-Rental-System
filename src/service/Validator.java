package service;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidNICorPassport(String id) {
        // Sri Lankan NIC
        if (id.matches("^[0-9]{9}[vVxX]$") || id.matches("^[0-9]{12}$")) return true;
        // International Passport: letters + digits 
        return id.matches("^[A-Za-z0-9]{6,12}$");
    }

    public static boolean isValidContact(String contact) {
        return contact.matches("^[0-9]{10}$");
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", email);
    }
}
