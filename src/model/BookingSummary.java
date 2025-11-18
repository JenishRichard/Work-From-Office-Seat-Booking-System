package model;

//BookingSummary used for printing success messages
public record BookingSummary(String empName, String department, String seatType, String bookingId) {

//Converts department name to uppercase
    @Override
    public String department() {
        return department.toUpperCase();
    }

//Used for showing success message after booking
    public String successLine() {
        return String.format("Booking Successful! for %s from %s Department",
                empName,
                department()
        );
    }

//Optional formatted line if needed we can use instead of successLine()
    public String formattedSummary() {
        return String.format("%s from %s Department booked a %s [Booking ID: %s]",
                empName,
                department(),
                seatType,
                bookingId
        );
    }
}
