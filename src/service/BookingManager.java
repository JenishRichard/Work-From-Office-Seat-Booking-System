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

    public void showSeatAvailability() {
        long regular = seats.stream().filter(s -> s.getSeatType().equals("Regular Seat") && s.isAvailable()).count();
        long window = seats.stream().filter(s -> s.getSeatType().equals("Window Seat") && s.isAvailable()).count();
        long corner = seats.stream().filter(s -> s.getSeatType().equals("Corner Seat") && s.isAvailable()).count();
        long meeting = seats.stream().filter(s -> s.getSeatType().equals("Meeting Room") && s.isAvailable()).count();

        System.out.println("\n--- Seat Availability ---");
        System.out.printf("Regular Seats: %d / 25%n", regular);
        System.out.printf("Window Seats:  %d / 10%n", window);
        System.out.printf("Corner Seats:  %d / 15%n", corner);
        System.out.printf("Meeting Rooms: %d / 5%n", meeting);
        System.out.println();
    }
    @Override
    public void bookSeat(String empId, int seatTypeChoice) {

        String seatType = switch (seatTypeChoice) {
            case 1 -> "Regular Seat";
            case 2 -> "Window Seat";
            case 3 -> "Corner Seat";
            case 4 -> "Meeting Room";
            default -> null;
        };

        if (seatType == null) {
            System.out.println("Invalid seat type!");
            return;
        }
        Optional<Seat> seatOpt = seats.stream()
                .filter(s -> s.getSeatType().equals(seatType) && s.isAvailable())
                .findFirst();

        if (seatOpt.isEmpty()) {
            System.out.println("No available " + seatType + "s at the moment!");
            return;
        }

        Seat seat = seatOpt.get();
        seat.setAvailable(false);

        System.out.print("Enter employee name: ");
        String name = sc.next();

        System.out.print("Enter department: ");
        String dept = sc.next();

        System.out.print("Do you need lunch for the day? (Y/N): ");
        String foodInput = sc.next();
        FoodOption food = foodInput.equalsIgnoreCase("Y") ? FoodOption.REQUIRED : FoodOption.NOT_REQUIRED;

        Employee emp = new Employee(empId, name, dept);
        Booking booking = new Booking(
                UUID.randomUUID().toString(),
                emp,
                seat,
                LocalDate.now(),
                BookingStatus.BOOKED,
                food
        );
        bookings.add(booking);
        System.out.println("\n====================================");
        System.out.println("Booking Successful!");
        System.out.printf("Employee: %s %n", emp.getEmpName());
        System.out.printf("Seat: %s (Seat ID: %d)%n", seat.getSeatType(), seat.getSeatId());
        System.out.printf("Food: %s%n", food);
        System.out.printf("Booking ID: %s%n", booking.getBookingId());
        System.out.printf("Date: %s%n", booking.getBookingDate());
        System.out.println("\"Thanks for Booking. Have a nice Day, " + emp.getEmpName() + "!\"");
        System.out.println("***************************************************");
        showSeatAvailability();
    }

    @Override
    public void cancelBooking(String bookingId) {
        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.getBookingId().equals(bookingId)) {
                b.getSeat().setAvailable(true);
                iterator.remove();

                System.out.println("\n❌ Booking cancelled for " + b.getEmployee().getEmpName());
                showSeatAvailability();
                return;
            }
        }
        System.out.println("Booking ID not found!");
    }

    @Override
    public void showAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings yet!");
            return;
        }

        System.out.println("\n--- All Current Bookings ---");
        for (Booking b : bookings) {
            Seat seat = b.getSeat();
            System.out.printf("Employee=%s, Dept=%s, SeatType=%s, SeatID=%d, Food=%s, BookingID=%s%n",
                    b.getEmployee().getEmpName(),
                    b.getEmployee().getDepartment(),
                    seat.getSeatType(),
                    seat.getSeatId(),
                    b.getFoodOption(),
                    b.getBookingId()
            );
        }
    }

    public void showAvailableSeats() {
        System.out.println("\n--- Available Seats ---");
        seats.stream()
                .filter(Seat::isAvailable)
                .forEach(s -> System.out.printf("Seat ID=%d, Type=%s%n", s.getSeatId(), s.getSeatType()));
    }
}
