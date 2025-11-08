package main;

import java.util.Scanner;
import service.BookingManager;

public class AppLauncher {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        var manager = new BookingManager();

        int choice;
        do {
            System.out.println("\n===== Work From Office Seat Booking System =====");
            System.out.println("1. Book Seat");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Show All Bookings");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    manager.showSeatAvailability();
                    System.out.print("Enter Employee ID: ");
                    var empId = sc.next();
                    System.out.println("Enter Seat Type From Below ");
                    System.out.println("1 -> Regular Seat");
                    System.out.println("2 -> Window Seat");
                    System.out.println("3 -> Corner Seat");
                    System.out.println("4 -> Meeting Room");
                    System.out.print("Seat: ");
                    var seatTypeChoice = sc.nextInt();

                    manager.bookSeat(empId, seatTypeChoice);
                }
                case 2 -> {
                    System.out.print("Enter Booking ID: ");
                    var bookingId = sc.next();
                    manager.cancelBooking(bookingId);
                }
                case 3 -> manager.showAllBookings();
                case 4 -> System.out.println("Goodbye");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }
}
