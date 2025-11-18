package model;

import java.io.Serializable;

public class Invoice implements Serializable {
    private static int invoiceCounter = 1000;
    private final String invoiceId;
    private final Reservation reservation;
    private final Car car;
    private final Customer customer;
    private final double basePrice;
    private final double discountAmount;
    private final double extraKmCharge;
    private final double taxAmount;
    private final double deposit;
    private final double finalPayable;

    //   Constructor matches exactly what RentalService sends
    public Invoice(Reservation reservation, Car car, Customer customer,
                   double basePrice, double discountAmount,
                   double extraKmCharge, double taxAmount,
                   double deposit, double finalPayable) {
        this.invoiceId = "I" + (++invoiceCounter);
        this.reservation = reservation;
        this.car = car;
        this.customer = customer;
        this.basePrice = basePrice;
        this.discountAmount = discountAmount;
        this.extraKmCharge = extraKmCharge;
        this.taxAmount = taxAmount;
        this.deposit = deposit;
        this.finalPayable = finalPayable;
    }

    public String getInvoiceId() { return invoiceId; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------- EcoRide Invoice ---------\n");
        sb.append("Invoice ID: ").append(invoiceId).append("\n");
        sb.append("Customer: ").append(customer.getName()).append(" (")
          .append(customer.getNicOrPassport()).append(")\n");
        sb.append("Car: ").append(car.getModel()).append(" | Category: ")
          .append(car.getCategory().getDisplayName()).append("\n");
        sb.append("Rental Duration: ").append(reservation.getDays()).append(" days\n");
        sb.append("Planned km: ").append(reservation.getExpectedKm()).append(" km\n");
        sb.append("Base Price: LKR ").append(basePrice).append("\n");
        sb.append("Discount: -LKR ").append(discountAmount).append("\n");
        sb.append("Extra Km Charge: LKR ").append(extraKmCharge).append("\n");
        sb.append("Tax: LKR ").append(taxAmount).append("\n");
        sb.append("Deposit: LKR ").append(deposit).append("\n");
        sb.append("Final Payable: LKR ").append(finalPayable).append("\n");
        sb.append("---------------------------\n");
        return sb.toString();
    }
}
