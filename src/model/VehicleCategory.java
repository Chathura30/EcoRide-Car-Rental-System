package model;

import java.io.Serializable;

public enum VehicleCategory implements Serializable {
    COMPACT_PETROL("Compact Petrol Car", 5000, 100, 50, 0.10),
    HYBRID("Hybrid Car", 7500, 150, 60, 0.12),
    ELECTRIC("Electric Car", 10000, 200, 40, 0.08),
    LUXURY_SUV("Luxury SUV", 15000, 250, 75, 0.15);

    private final String displayName;
    private final double dailyFee;
    private final int freeKmPerDay;
    private final double extraKmCharge;
    private final double taxRate;

    VehicleCategory(String displayName, double dailyFee, int freeKmPerDay, double extraKmCharge, double taxRate) {
        this.displayName = displayName;
        this.dailyFee = dailyFee;
        this.freeKmPerDay = freeKmPerDay;
        this.extraKmCharge = extraKmCharge;
        this.taxRate = taxRate;
    }

    public String getDisplayName() { return displayName; }
    public double getDailyFee() { return dailyFee; }
    public int getFreeKmPerDay() { return freeKmPerDay; }
    public double getExtraKmCharge() { return extraKmCharge; }
    public double getTaxRate() { return taxRate; }

    @Override
    public String toString() { return displayName; }
}
