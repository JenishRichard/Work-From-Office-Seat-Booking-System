package service;

import model.*;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements IBookable {
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Seat> seats = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    public BookingManager() {
        seats.add(new Seat(1, "Regular Seat", true));
        seats.add(new Seat(2, "Window Seat", true));
        seats.add(new Seat(3, "Corner Seat", true));
    }

    @Override
    public void bookSeat(String empId, int seatId) {
        var seatOpt = seats.stream()
                .filter(s -> s.getSeatId() == seatId && s.isAvailable())
                .findFirst();

        if (seatOpt.isEmpty()) {
            System.out.println("Seat not available or invalid ID!");
            return;
        }

        var seat = seatOpt.get();
        seat.setAvailable(false);

        System.out.print("Enter employee name: ");
        var name = sc.next();
        System.out.print("Enter department: ");
        var dept = sc.next();
        System.out.print("Do you need lunch for the day? (Y/N): ");
        var foodInput = sc.next();
        var food = foodInput.equalsIgnoreCase("Y") ? FoodOption.REQUIRED : FoodOption.NOT_REQUIRED;

        var emp = new Employee(empId, name, dept);
        var booking = new Booking(
                UUID.randomUUID().toString(),
                emp,
                seat,
                LocalDate.now(),
                BookingStatus.BOOKED,
                food
        );
        bookings.add(booking);

      
        System.out.println(String.format(
        	    "Booking successful for %s [Seat=%s, Food=%s] (Booking ID: %s)",
        	    emp.getEmpName(), seat.getSeatType(), food, booking.getBookingId()
        	));

    }

    @Override
    public void cancelBooking(String bookingId) {
        for (var b : bookings) {
            if (b.getBookingId().equals(bookingId)) 
            {
                b.getSeat().setAvailable(true);
                bookings.remove(b);
                System.out.println(String.format("Booking cancelled for %s", b.getEmployee().getEmpName()));
                return;
            }
        }
        System.out.println("Booking ID not found!");
    }

    @Override
    public void showAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet!");
            return;
        }

        System.out.println("\n--- All Bookings ---");
        for (var b : bookings) {
            if (b.getSeat() instanceof Seat seat) {
                System.out.println(String.format(
                        "Employee=%s, Seat=%s, Food=%s",
                        b.getEmployee().getEmpName(),
                        seat.getSeatType(),
                        b.getFoodOption()
                ));
            }
        }
    }
}
