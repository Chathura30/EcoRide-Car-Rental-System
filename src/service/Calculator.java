package service;

import model.Reservation;
import model.Car;
import model.VehicleCategory;

public class Calculator {
    // Business consCalculatortant
    public static final double BOOKING_DEPOSIT = 5000.0;

    public static class CalculationResult {
        public final double basePrice;
        public final double discount;
        public final double extraKmCharge;
        public final double tax;
        public final double finalPayable;

        public CalculationResult(double basePrice, double discount, double extraKmCharge, double tax, double finalPayable) {
            this.basePrice = basePrice;
            this.discount = discount;
            this.extraKmCharge = extraKmCharge;
            this.tax = tax;
            this.finalPayable = finalPayable;
        }
    }

    public static CalculationResult calculate(Car car, Reservation reservation) {
        VehicleCategory cat = car.getCategory();

        // Base price = dailyFee × number of days
        double basePrice = cat.getDailyFee() * reservation.getDays();

        // Discount: if days >= 7 → 10% of base price
        double discount = 0.0;
        if (reservation.getDays() >= 7) {
            discount = basePrice * 0.10; 
        }

        // Extra KM charges
        int freeKmTotal = cat.getFreeKmPerDay() * reservation.getDays();
        int extraKm = Math.max(0, reservation.getExpectedKm() - freeKmTotal);
        double extraKmCharge = extraKm * cat.getExtraKmCharge();

        // Tax
        double taxableAmount = basePrice - discount + extraKmCharge;
        double tax = taxableAmount * cat.getTaxRate();

        // Final payable
        double finalPayable = taxableAmount + tax - reservation.getDeposit();

        return new CalculationResult(basePrice, discount, extraKmCharge, tax, finalPayable);
    }
}
