package service;

import model.*;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements IBookable {
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Seat> seats = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    public BookingManager() {
        initializeSeats();
    }
    public void showSeatAvailability() {
        long regular = seats.stream().filter(s -> s.getSeatType().equals("Regular Seat") && s.isAvailable()).count();
        long window = seats.stream().filter(s -> s.getSeatType().equals("Window Seat") && s.isAvailable()).count();
        long corner = seats.stream().filter(s -> s.getSeatType().equals("Corner Seat") && s.isAvailable()).count();
        long meeting = seats.stream().filter(s -> s.getSeatType().equals("Meeting Room") && s.isAvailable()).count();

        System.out.println("\n--- Seat Availability ---");
        System.out.println(String.format("Regular Seats: %d / 25", regular));
        System.out.println(String.format("Window Seats:  %d / 10", window));
        System.out.println(String.format("Corner Seats:  %d / 15", corner));
        System.out.println(String.format("Meeting Rooms: %d / 5", meeting));
        System.out.println();
    }
    private void initializeSeats() {
        for (int i = 1; i <= 25; i++) {
            seats.add(new Seat(i, "Regular Seat", true));
        }

        for (int i = 26; i <= 35; i++) {
            seats.add(new Seat(i, "Window Seat", true));
        }

        for (int i = 36; i <= 50; i++) {
            seats.add(new Seat(i, "Corner Seat", true));
        }

        for (int i = 51; i <= 55; i++) {
            seats.add(new Seat(i, "Meeting Room", true));
        }
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
        	    "\nBooking successful for \"%s\"\nSeat  = %s\nFood = %s\nBooking ID: %s\n\n\" Thanks for Booking. Have a nice Day %s\"",
        	    emp.getEmpName(), seat.getSeatType(), food, booking.getBookingId(), emp.getEmpName()
        	));
    }

    @Override
    public void cancelBooking(String bookingId) {
        for (var b : bookings) {
            if (b.getBookingId().equals(bookingId)) {
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
                        "Employee=%s, SeatType=%s, SeatID=%d, Food=%s",
                        b.getEmployee().getEmpName(),
                        seat.getSeatType(),
                        seat.getSeatId(),
                        b.getFoodOption()
                ));
            }
        }
    }

    public void showAvailableSeats() {
        System.out.println("\n--- Available Seats ---");
        for (var s : seats) {
            if (s.isAvailable()) {
                System.out.println(String.format("Seat ID=%d, Type=%s", s.getSeatId(), s.getSeatType()));
            }
        }
    }
}
