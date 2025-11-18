package app;

import model.*;
import service.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final RentalService service = new RentalService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== EcoRide Car Rental System ====");
            System.out.println("1) Manage Cars");
            System.out.println("2) List Cars");
            System.out.println("3) Register Customer");
            System.out.println("4) List Customers");
            System.out.println("5) Make Reservation");
            System.out.println("6) List Reservations");
            System.out.println("7) Generate Invoice");
            System.out.println("8) Search Reservation by Name");
            System.out.println("9) Search Reservation by Booking ID");
            System.out.println("10) View Reservations by Rental Date");
            System.out.println("11) Update Reservation");
            System.out.println("12) Cancel Reservation");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1 -> manageCarsMenu();
                case 2 -> listCars();
                case 3 -> registerCustomer();
                case 4 -> listCustomers();
                case 5 -> makeReservation();
                case 6 -> listReservations();
                case 7 -> generateInvoice();
                case 8 -> searchByName();
                case 9 -> searchById();
                case 10 -> searchByDate();
                case 11 -> updateReservationMenu();
                case 12 -> cancelReservationMenu();
                case 0 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------- Car Management Menu -------------------- //
    private static void manageCarsMenu() {
        while (true) {
            System.out.println("\n--- Manage Cars ---");
            System.out.println("1) Add Car");
            System.out.println("2) Update Car");
            System.out.println("3) Remove Car");
            System.out.println("0) Back to Main Menu");
            System.out.print("Choose: ");

            int c;
            try {
                c = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (c) {
                case 1 -> addCar();
                case 2 -> updateCar();
                case 3 -> removeCar();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------- Add Car -------------------- //
    private static void addCar() {
        System.out.print("Enter model: ");
        String model = sc.nextLine();

        System.out.println("Select category: ");
        for (int i = 0; i < VehicleCategory.values().length; i++) {
            System.out.println((i + 1) + ") " + VehicleCategory.values()[i].getDisplayName());
        }

        int cat;
        try {
            cat = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid category selection.");
            return;
        }

        if (cat < 1 || cat > VehicleCategory.values().length) {
            System.out.println("Invalid category selection.");
            return;
        }

        VehicleCategory category = VehicleCategory.values()[cat - 1];

        // Availability choice
        String availability = chooseAvailabilityInteractive("Set initial availability for the car:");

        Car car = service.addCar(model, category, availability);
        System.out.println("Car added successfully. ID: " + car.getCarId());
    }

    // -------------------- Update Car -------------------- //
    private static void updateCar() {
        System.out.print("Enter Car ID to update: ");
        String id = sc.nextLine();
        Car car = service.findCarById(id);
        if (car == null) { System.out.println("Car not found."); return; }

        System.out.print("Enter new model (leave blank to keep '" + car.getModel() + "'): ");
        String model = sc.nextLine();
        if (!model.isEmpty()) car.setModel(model);

        System.out.println("Select new category or 0 to keep '" + car.getCategory().getDisplayName() + "':");
        for (int i = 0; i < VehicleCategory.values().length; i++) {
            System.out.println((i + 1) + ") " + VehicleCategory.values()[i].getDisplayName());
        }

        String catStr = sc.nextLine();
        if (!catStr.equals("0") && !catStr.isEmpty()) {
            try {
                int cat = Integer.parseInt(catStr);
                if (cat >= 1 && cat <= VehicleCategory.values().length) {
                    car.setCategory(VehicleCategory.values()[cat - 1]);
                }
            } catch (NumberFormatException ignored) {}
        }

        System.out.println("Current availability: " + car.getAvailability());
        String newStatus = chooseAvailabilityInteractive("Choose new availability:");
        if (!newStatus.equalsIgnoreCase("KEEP_CURRENT")) {
            car.setAvailability(newStatus);
        }

        System.out.println("Car updated successfully.");
    }

    // -------------------- Remove Car -------------------- //
    private static void removeCar() {
        System.out.print("Enter Car ID to remove: ");
        String id = sc.nextLine();
        boolean success = service.removeCar(id);
        System.out.println(success ? "Car removed successfully." : "Car not found.");
    }

    // -------------------- Availability Selector -------------------- //
    private static String chooseAvailabilityInteractive(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("1) Available");
            System.out.println("2) Reserved");
            System.out.println("3) Under Maintenance");
            System.out.print("Choose: ");

            String sel = sc.nextLine();
            switch (sel) {
                case "1" -> {
                    return "Available";
                }
                case "2" -> {
                    return "Reserved";
                }
                case "3" -> {
                    return "Under Maintenance";
                }
                
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------- List Cars -------------------- //
    private static void listCars() {
        System.out.println("--- Cars ---");
        for (Car c : service.getCars()) System.out.println(c);
    }

    // -------------------- Register Customer -------------------- //
    private static void registerCustomer() {
        System.out.print("Enter NIC/Passport: ");
        String nic = sc.nextLine();
        if (!Validator.isValidNICorPassport(nic)) { System.out.println("Invalid NIC/Passport format!"); return; }

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter contact number (10 digits): ");
        String contact = sc.nextLine();
        if (!Validator.isValidContact(contact)) { System.out.println("Invalid contact number!"); return; }

        System.out.print("Enter email: ");
        String email = sc.nextLine();
        if (!Validator.isValidEmail(email)) { System.out.println("Invalid email!"); return; }

        Customer cust = service.addCustomer(nic, name, contact, email);
        System.out.println("Customer registered. ID: " + cust.getCustomerId());
    }

    // -------------------- List Customers -------------------- //
    private static void listCustomers() {
        for (Customer c : service.getCustomers()) System.out.println(c);
    }

    // -------------------- Make Reservation -------------------- //
    private static void makeReservation() {
        System.out.print("Enter Car ID: ");
        String carId = sc.nextLine();

        System.out.print("Enter Customer ID: ");
        String custId = sc.nextLine();

        System.out.print("Start date (YYYY-MM-DD): ");
        LocalDate start;
        try { start = LocalDate.parse(sc.nextLine()); }
        catch (Exception e) { System.out.println("Invalid date."); return; }

        System.out.print("Days: ");
        int days;
        try { days = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Invalid days."); return; }

        System.out.print("Expected total km: ");
        int km;
        try { km = Integer.parseInt(sc.nextLine()); }
        catch (NumberFormatException e) { System.out.println("Invalid km."); return; }

        Reservation res = service.makeReservation(carId, custId, start, days, km);
        System.out.println(res != null ? "Reservation created. ID: " + res.getReservationId()
                                       : "Error making reservation.");
    }

    // -------------------- List Reservations -------------------- //
    private static void listReservations() {
        for (Reservation r : service.getReservations()) System.out.println(r);
    }

    // -------------------- Invoice -------------------- //
    private static void generateInvoice() {
        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();
        Invoice inv = service.generateInvoice(id);
        System.out.println(inv != null ? inv : "Reservation not found.");
    }

    // -------------------- Search -------------------- //
    private static void searchByName() {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        var results = service.findReservationsByCustomerName(name);
        if (results.isEmpty()) System.out.println("No reservations found.");
        else results.forEach(System.out::println);
    }

    private static void searchById() {
        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();
        Reservation r = service.findReservationById(id);
        System.out.println(r != null ? r : "Reservation not found.");
    }

    private static void searchByDate() {
        System.out.print("Enter Rental Date (YYYY-MM-DD): ");
        LocalDate date;
        try { date = LocalDate.parse(sc.nextLine()); }
        catch (Exception e) { System.out.println("Invalid date."); return; }

        var results = service.findReservationsByDate(date);
        if (results.isEmpty()) System.out.println("No reservations found.");
        else results.forEach(System.out::println);
    }

    // -------------------- Update Reservation -------------------- //
    private static void updateReservationMenu() {
        System.out.print("Enter Reservation ID to update: ");
        String id = sc.nextLine();
        Reservation r = service.findReservationById(id);

        if (r == null) { System.out.println("Reservation not found."); return; }
        if (!r.canModifyOrCancel()) { System.out.println("Cannot update. More than 2 days passed."); return; }

        System.out.print("New days: ");
        int days = Integer.parseInt(sc.nextLine());
        System.out.print("New expected km: ");
        int km = Integer.parseInt(sc.nextLine());

        boolean success = service.updateReservation(id, days, km);
        System.out.println(success ? "Reservation updated." : "Update failed.");
    }

    // --------------------CANCEL RESERVATION -------------------- //
    private static void cancelReservationMenu() {
        System.out.print("Enter Reservation ID to cancel: ");
        String id = sc.nextLine();

        Reservation r = service.findReservationById(id);

        if (r == null) {
            System.out.println("Reservation not found.");
            return;
        }

        // Show reservation details BEFORE cancellation
        System.out.println("\n--- Reservation Details ---");
        System.out.println("Reservation ID : " + r.getReservationId());
        System.out.println("Car ID         : " + r.getCarId());
        System.out.println("Customer ID    : " + r.getCustomerId());
        System.out.println("Start Date     : " + r.getStartDate());
        System.out.println("Days           : " + r.getDays());
        System.out.println("Expected Km    : " + r.getExpectedKm());
        System.out.println("Booking Date   : " + r.getBookingDate());
        System.out.println("-----------------------------");

        if (!r.canModifyOrCancel()) {
            System.out.println("Cannot cancel. More than 2 days passed.");
            return;
        }

        System.out.print("\nAre you sure you want to cancel this reservation? (Y/N): ");
        String confirm = sc.nextLine().trim().toUpperCase();
        if (!confirm.equals("Y")) {
            System.out.println("Cancellation aborted.");
            return;
        }

        boolean success = service.cancelReservation(id);

        System.out.println(success
                ? "Reservation cancelled successfully."
                : "Cancellation failed.");
    }
}
