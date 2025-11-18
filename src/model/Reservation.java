package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private static int resCounter = 1000;
    private final String reservationId;
    private final String carId;
    private final String customerId;
    private final LocalDate startDate;
    private int days;
    private int expectedKm;
    private final double deposit;
    private final LocalDate bookingDate;

    public Reservation(String carId, String customerId, LocalDate startDate, int days, int expectedKm, double deposit) {
        this.reservationId = "R" + (++resCounter);
        this.carId = carId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.days = days;
        this.expectedKm = expectedKm;
        this.deposit = deposit;
        this.bookingDate = LocalDate.now();
    }

    public String getReservationId() { return reservationId; }
    public String getCarId() { return carId; }
    public String getCustomerId() { return customerId; }
    public LocalDate getStartDate() { return startDate; }
    public int getDays() { return days; }
    public int getExpectedKm() { return expectedKm; }
    public double getDeposit() { return deposit; }
    public LocalDate getBookingDate() { return bookingDate; }

    public void setDays(int days) { this.days = days; }
    public void setExpectedKm(int expectedKm) { this.expectedKm = expectedKm; }

    // Can modify or cancel within 2 days of booking
    public boolean canModifyOrCancel() {
        LocalDate deadline = bookingDate.plusDays(2);
        return !LocalDate.now().isAfter(deadline);
    }

    @Override
    public String toString() {
        return String.format("%s | Car:%s | Cust:%s | Start:%s | %d days | Expected Km:%d", 
            reservationId, carId, customerId, startDate, days, expectedKm);
    }
}
