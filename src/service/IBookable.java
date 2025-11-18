package service;

public interface IBookable {

    void showSeatAvailability();

//Books a seat for an employee based on seat type
    void bookSeat(String empId, int seatType);
    
    void bookSeat(String empId);
    void cancelBooking(String bookingId);
    void showAllBookings(); 
    void bookCab(String empId, int cabChoice);
    void showAllCabBookings();

//Default method to display the Work From office welcome in console
    default void showSystemInfo() {
        System.out.println("***********************************************");
        System.out.println("Welcome to Work From Office Seat Booking System");
        System.out.println("***********************************************");
    }

//Below is Static method which is used to show user option
    static void displayHelp() {
        System.out.println("Use Below Options to Book, Cancel or Book Cab\n");
    }
}
