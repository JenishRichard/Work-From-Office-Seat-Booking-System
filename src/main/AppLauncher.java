package main;

import java.util.Scanner;
import service.*;

//Main class that launches the application
public class AppLauncher {
    public static void main(String[] args) {

        try (var sc = new Scanner(System.in)) {
            var manager = new BookingManager();   
            manager.showSystemInfo();

            int choice;

            do {
                //Show menu options
                IBookable.displayHelp();
                System.out.println("1. Book Seat");
                System.out.println("2. Cancel Booking");
                System.out.println("3. Show All Seat Bookings");
                System.out.println("4. Show All Cab Bookings");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1 -> {
                        manager.showSeatAvailability();
                        System.out.print("Enter Employee ID: ");
                        var empId = sc.next();

                        System.out.println("Seat Types :");
                        System.out.println("1 - Regular");
                        System.out.println("2 - Window");
                        System.out.println("3 - Corner");
                        System.out.println("4 - Meeting");
                        System.out.print("Enter Seat Type: ");
                        var seatTypeChoice = sc.nextInt();

                        manager.bookSeat(empId, seatTypeChoice);
                    }

                    case 2 -> {
                        System.out.print("Enter Booking ID: ");
                        var bookingId = sc.next();
                        manager.cancelBooking(bookingId);
                    }

                    case 3 -> manager.showAllBookings();      
                    
                    case 4 -> manager.showAllCabBookings();   
                    
                    case 5 -> System.out.println("Goodbye!");

                    default -> System.out.println("Invalid choice! Please try again.");
                }

            } while (choice != 5);
        }   
    }
}
