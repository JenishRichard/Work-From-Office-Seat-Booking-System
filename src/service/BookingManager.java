package service;

import model.*;
import exceptions.BookingNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class BookingManager implements IBookable {
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Seat> seats = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private final String[] departments = {"IT", "HR", "Finance", "Admin"}; 

    public BookingManager() {
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 1; i <= 25; i++) seats.add(new RegularSeat(i));
        for (int i = 26; i <= 35; i++) seats.add(new WindowSeat(i));
        for (int i = 36; i <= 50; i++) seats.add(new CornerSeat(i));
        for (int i = 51; i <= 55; i++) seats.add(new MeetingRoom(i));
    }

    public void showSeatAvailability() {
        System.out.println("\n--- Seat Availability ---");

        Map<String, Long> availability = new LinkedHashMap<>();

        seats.stream()
                .filter(Seat::isAvailable)
                .forEach(s -> 
                    availability.merge(s.getSeatType(), 1L, Long::sum)
                );

        if (availability.isEmpty()) {
            System.out.println("No seats available!");
            return;
        }

        availability.forEach((type, count) ->
            System.out.printf("Type = %s : %d Seats Available%n", type, count)
        );
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

        Predicate<Seat> availableSeat = s -> s.getSeatType().equals(seatType) && s.isAvailable();
        Optional<Seat> seatOpt = seats.stream().filter(availableSeat).findFirst();

        if (seatOpt.isEmpty()) {
            System.out.println("No available " + seatType + "s right now!");
            return;
        }

        Seat seat = seatOpt.get();
        seat.setAvailable(false);

        System.out.print("Enter employee name: ");
        String name = sc.next();

        System.out.print("Enter department: ");
        String dept = sc.next();

        System.out.print("Need lunch (Y/N): ");
        String foodInput = sc.next();
        FoodOption food = foodInput.equalsIgnoreCase("Y") ? FoodOption.REQUIRED : FoodOption.NOT_REQUIRED;

        Employee emp = new Employee(empId, name, dept);
        Booking booking = new Booking(UUID.randomUUID().toString(), emp, seat, LocalDate.now(), BookingStatus.BOOKED, food);
        bookings.add(booking);

        System.out.println("\nBooking Successful!\n");

        System.out.println("Booking Details :");
        System.out.printf("Employee Name = %s%n", emp.getEmpName());
        System.out.printf("Seat = %s%n", seat.getSeatType());
        System.out.printf("Food = %s%n", food);
        System.out.printf("Booking ID = %s%n", booking.getBookingId());
        System.out.println("--------------------------------------------------------------------------------------------------");
    }
 
    public void bookSeat(String empId) {
        bookSeat(empId, 1); 
    }

    @Override
    public void cancelBooking(String bookingId) {
        try {
            Booking booking = bookings.stream()
                    .filter(b -> b.getBookingId().equals(bookingId))
                    .findFirst()
                    .orElseThrow(() -> new BookingNotFoundException("Booking not found!"));
            booking.getSeat().setAvailable(true);
            bookings.remove(booking);
            System.out.println("Booking cancelled for " + booking.getEmployee().getEmpName());
        } catch (BookingNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet!");
            return;
        }
        System.out.println("\n--- All Current Bookings ---");
        bookings.forEach(System.out::println); 
    }


    public void printBookings(Booking... list) {
        for (Booking b : list) System.out.println(b);
    }
}
