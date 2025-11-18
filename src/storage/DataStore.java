package storage;

import model.Car;
import model.Customer;
import model.Reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static final String CARS_FILE = "cars.dat";
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String RESERVATIONS_FILE = "reservations.dat";

    public static void saveCars(List<Car> cars) throws IOException { saveObject(cars, CARS_FILE); }
    public static List<Car> loadCars() throws IOException, ClassNotFoundException { return loadList(CARS_FILE); }

    public static void saveCustomers(List<Customer> customers) throws IOException { saveObject(customers, CUSTOMERS_FILE); }
    public static List<Customer> loadCustomers() throws IOException, ClassNotFoundException { return loadList(CUSTOMERS_FILE); }

    public static void saveReservations(List<Reservation> reservations) throws IOException { saveObject(reservations, RESERVATIONS_FILE); }
    public static List<Reservation> loadReservations() throws IOException, ClassNotFoundException { return loadList(RESERVATIONS_FILE); }

    private static void saveObject(Object obj, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(obj);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> loadList(String filename) throws IOException, ClassNotFoundException {
        File f = new File(filename);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        }
    }
}
