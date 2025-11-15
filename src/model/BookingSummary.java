package model;

public record BookingSummary(String empName, String department, String seatType, String bookingId) {

    public String successLine() {
        return String.format("Booking Successful! for %s from %s Department", empName, department);
    }

    public String formattedSummary() {
        return String.format("%s from %s Department booked a %s [Booking ID: %s]",
                empName, department, seatType, bookingId);
    }
}
