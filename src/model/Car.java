package model;

import java.io.Serializable;

public class Car implements Serializable {
    private static int carCounter = 1000;
    private final String carId;
    private String model;
    private VehicleCategory category;
    private String availability;

    public Car(String model, VehicleCategory category) {
        this.carId = "C" + (++carCounter);
        this.model = model;
        this.category = category;
        this.availability = "Available";
    }

    public String getCarId() { return carId; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public VehicleCategory getCategory() { return category; }
    public void setCategory(VehicleCategory category) { this.category = category; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    @Override
    public String toString() {
        return String.format("%s | %s (%s) - %s", carId, model, category.getDisplayName(), availability);
    }
}
