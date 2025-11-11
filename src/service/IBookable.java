package service;

public interface IBookable {
    void bookSeat(String empId, int seatType);
    void cancelBooking(String bookingId);
    void showAllBookings();

    default void showSystemInfo() {
    	System.out.println("***********************************************");
        System.out.println("Welcome to Work From Office Seat Booking System");
        System.out.println("***********************************************");
    }

    static void displayHelp() {
        System.out.println("Use Below Options to Book or Cancel Your Seat\n");
    }
}
