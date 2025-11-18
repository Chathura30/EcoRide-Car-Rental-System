package service;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalService {

    private final List<Car> cars = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();

    public RentalService() {
        if (cars.isEmpty()) {
            cars.add(new Car("Toyota Aqua", VehicleCategory.COMPACT_PETROL));
            cars.add(new Car("Nissan Leaf", VehicleCategory.ELECTRIC));
            cars.add(new Car("BMW X5", VehicleCategory.LUXURY_SUV));
        }
        if (customers.isEmpty()) {
            customers.add(new Customer("123456789V", "Saman Perera", "0771234567", "saman@example.com"));
            customers.add(new Customer("N1234567", "David Silva", "0719876543", "david@gmail.com"));
        }
    }

    // -------------------- Car Management -------------------- //
    // Original addCar kept for compatibility 
    public Car addCar(String model, VehicleCategory category) {
        Car car = new Car(model, category);
        cars.add(car);
        return car;
    }

    // Overload to set availability on creation
    public Car addCar(String model, VehicleCategory category, String availability) {
        Car car = new Car(model, category);
        if (availability != null && !availability.isBlank()) {
            car.setAvailability(availability);
        }
        cars.add(car);
        return car;
    }

    public List<Car> getCars() { return cars; }

    public Car findCarById(String id) {
        for (Car c : cars) if (c.getCarId().equalsIgnoreCase(id)) return c;
        return null;
    }

    public void updateCar(Car car, String newModel, VehicleCategory newCategory, String newStatus) {
        if (newModel != null && !newModel.isBlank()) car.setModel(newModel);
        if (newCategory != null) car.setCategory(newCategory);
        if (newStatus != null && !newStatus.isBlank()) car.setAvailability(newStatus);
    }

    public boolean removeCar(String id) {
        Car car = findCarById(id);
        if (car != null) return cars.remove(car);
        return false;
    }

    // -------------------- Customer Management -------------------- //
    public Customer addCustomer(String nicOrPassport, String name, String contact, String email) {
        Customer c = new Customer(nicOrPassport, name, contact, email);
        customers.add(c);
        return c;
    }

    public List<Customer> getCustomers() { return customers; }

    public Customer findCustomerById(String id) {
        for (Customer c : customers) if (c.getCustomerId().equalsIgnoreCase(id)) return c;
        return null;
    }

    // -------------------- Reservation Management -------------------- //
    public Reservation makeReservation(String carId, String customerId, LocalDate startDate, int days, int expectedKm) {
        Car car = findCarById(carId);
        if (car == null) {
            System.out.println("Error: Car not found.");
            return null;
        }
        if (!car.getAvailability().equalsIgnoreCase("Available")) {
            System.out.println("Error: Car is not available.");
            return null;
        }

        Customer cust = findCustomerById(customerId);
        if (cust == null) {
            System.out.println("Error: Customer not found.");
            return null;
        }

        // Booking lead time: at least 3 days in advance
        if (!startDate.isAfter(LocalDate.now().plusDays(2))) {
            System.out.println("Error: Booking must be at least 3 days in advance.");
            return null;
        }

        Reservation res = new Reservation(carId, customerId, startDate, days, expectedKm, 5000);
        reservations.add(res);
        car.setAvailability("Reserved");
        return res;
    }

    public List<Reservation> getReservations() { return reservations; }

    // -------------------- Update / Cancel Reservation -------------------- //
    public Reservation findReservationById(String id) {
        for (Reservation r : reservations) if (r.getReservationId().equalsIgnoreCase(id)) return r;
        return null;
    }

    public boolean updateReservation(String reservationId, int newDays, int newKm) {
        Reservation res = findReservationById(reservationId);
        if (res == null) return false;
        if (!res.canModifyOrCancel()) return false;

        res.setDays(newDays);
        res.setExpectedKm(newKm);
        return true;
    }

    public boolean cancelReservation(String reservationId) {
        Reservation res = findReservationById(reservationId);
        if (res == null) return false;
        if (!res.canModifyOrCancel()) return false;

        // Make the car available again
        Car car = findCarById(res.getCarId());
        if (car != null) car.setAvailability("Available");

        return reservations.remove(res);
    }

    // -------------------- Invoice -------------------- //
    public Invoice generateInvoice(String reservationId) {
        Reservation res = findReservationById(reservationId);
        if (res == null) return null;
        Car car = findCarById(res.getCarId());
        Customer cust = findCustomerById(res.getCustomerId());
        Calculator.CalculationResult cr = Calculator.calculate(car, res);
        return new Invoice(res, car, cust, cr.basePrice, cr.discount, cr.extraKmCharge, cr.tax, res.getDeposit(), cr.finalPayable);
    }

    // -------------------- Search -------------------- //
    public List<Reservation> findReservationsByCustomerName(String name) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            Customer c = findCustomerById(r.getCustomerId());
            if (c != null && c.getName().toLowerCase().contains(name.toLowerCase())) result.add(r);
        }
        return result;
    }

    public List<Reservation> findReservationsByDate(LocalDate date) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) if (r.getStartDate().equals(date)) result.add(r);
        return result;
    }
}
