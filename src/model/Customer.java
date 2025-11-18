package model;

import java.io.Serializable;

public class Customer implements Serializable {
    private static int custCounter = 1000;
    private final String customerId;
    private final String nicOrPassport;
    private String name;
    private String contactNumber;
    private String email;

    public Customer(String nicOrPassport, String name, String contactNumber, String email) {
        this.customerId = "CU" + (++custCounter);
        this.nicOrPassport = nicOrPassport;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getCustomerId() { return customerId; }
    public String getNicOrPassport() { return nicOrPassport; }
    public String getName() { return name; }
    public String getContactNumber() { return contactNumber; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("%s | %s (%s)", customerId, name, nicOrPassport);
    }
}
