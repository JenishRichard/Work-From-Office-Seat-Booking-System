package service;

import model.*;
import exceptions.BookingNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

//BookingManager handles Seat Booking, Cab Booking
public class BookingManager implements IBookable {

    private final List<Booking> bookings = new ArrayList<>();
    private final List<Seat> seats = new ArrayList<>();
    private final List<CabBooking> cabBookings = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    public BookingManager() {
        initializeSeats();
    }

    //Creates all seats during application startup
    private void initializeSeats() {
        for (int i = 1; i <= 25; i++) seats.add(new RegularSeat(i));
        for (int i = 26; i <= 35; i++) seats.add(new WindowSeat(i));
        for (int i = 36; i <= 50; i++) seats.add(new CornerSeat(i));
        for (int i = 51; i <= 55; i++) seats.add(new MeetingRoom(i));
    }

    //Shows seat count grouped by seat type
    @Override
    public void showSeatAvailability() {
        System.out.println("\n--- Seat Availability ---");

        Map<String, Long> availability = new LinkedHashMap<>();

        seats.stream()
                .filter(Seat::isAvailable)
                .forEach(s -> availability.merge(s.getSeatType(), 1L, Long::sum));

        availability.forEach((type, count) ->
                System.out.printf("%s - %d Available%n", type, count)
        );
    }

    //Main seat booking function
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

        //Lambda predicate filters available matching seat
        Predicate<Seat> availableSeat =
                s -> s.getSeatType().equals(seatType) && s.isAvailable();

        Optional<Seat> seatOpt = seats.stream().filter(availableSeat).findFirst();

        if (seatOpt.isEmpty()) {
            System.out.println("No available " + seatType + " right now!");
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
        FoodOption food = foodInput.equalsIgnoreCase("Y")
                ? FoodOption.REQUIRED : FoodOption.NOT_REQUIRED;

        Employee emp = new Employee(empId, name, dept);

        //Generate a 6 digit booking ID
        String bookingId = String.format("%06d", new Random().nextInt(999999));

        Booking booking = new Booking(
                bookingId,
                emp,
                seat,
                LocalDate.now(),
                BookingStatus.BOOKED,
                food
        );

        bookings.add(booking);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        System.out.print("Will you work after 6 PM? (Y/N): ");
        String workLate = sc.next();

        CabBooking cabBooking = null;

        if (workLate.equalsIgnoreCase("Y")) {

            System.out.print("Do you need a cab? (Y/N): ");
            String needCab = sc.next();

            if (needCab.equalsIgnoreCase("Y")) {

                //Select route
                System.out.println("\nAvailable Routes:");
                System.out.println("1 - Solinganalur");
                System.out.println("2 - Marina Beach");
                System.out.println("3 - Anna Nagar");
                System.out.print("Select Route: ");
                int routeChoice = sc.nextInt();

                Route route = switch (routeChoice) {
                    case 1 -> Route.Route_Sollinganalur;
                    case 2 -> Route.Route_Marina_Beach;
                    case 3 -> Route.Route_AnnaNagar;
                    default -> null;
                };

                //Select pickup point
                System.out.println("\nPickup Points:");
                System.out.println("1 - Gate 1");
                System.out.println("2 - Gate 2");
                System.out.println("3 - Canteen");
                System.out.println("4 - Parking Area");
                System.out.print("Enter pickup choice: ");
                int ppChoice = sc.nextInt();
                System.out.println();
                PickupPoint pickup = switch (ppChoice) {
                    case 1 -> PickupPoint.GATE_1;
                    case 2 -> PickupPoint.GATE_2;
                    case 3 -> PickupPoint.CANTEEN;
                    case 4 -> PickupPoint.PARKING_AREA;
                    default -> null;
                };

                if (route != null && pickup != null) {
                    String cabBookingId = String.format("%06d", new Random().nextInt(999999));
                    cabBooking = new CabBooking(
                            cabBookingId,
                            emp,
                            route,
                            pickup,
                            LocalDate.now()
                    );

                    cabBookings.add(cabBooking);

                }
            }
        }

        //Print seat booking success
        BookingSummary summary = new BookingSummary(
                emp.getEmpName(),
                emp.getDepartment(),
                seat.getSeatType(),
                bookingId
        );

        System.out.println(summary.successLine());
        System.out.println();

        System.out.println("Booking Details:\n");
        System.out.printf("Employee Name = %s%n", emp.getEmpName());
        System.out.printf("Seat = %s%n", seat.getSeatType());
        System.out.printf("Food = %s%n", food);
        System.out.printf("Date = %s%n", booking.getBookingDate().format(formatter));
        System.out.printf("Booking ID = %s%n", bookingId);
        System.out.println();

        //Show cab booking details only if cabBooking != null
        if (cabBooking != null) {
            System.out.println("Cab Booking Details:\n");
            System.out.println("Route = " + cabBooking.route());
            System.out.println("Pickup = " + cabBooking.pickupPoint());
            System.out.println("Cab Booking ID = " + cabBooking.cabBookingId());
            System.out.println();
        }

        System.out.println("------------------------------------------------------------");
    }

    //Overloaded - used if seat type not selected
    public void bookSeat(String empId) {
        bookSeat(empId, 1);
    }

    //Cancel booking by ID
    @Override
    public void cancelBooking(String bookingId) {
        try {
            Booking booking = bookings.stream()
                    .filter(b -> b.getBookingId().equals(bookingId))
                    .findFirst()
                    .orElseThrow(() ->
                            new BookingNotFoundException("Booking not found!")
                    );

            booking.getSeat().setAvailable(true);
            bookings.remove(booking);

            System.out.println("Booking cancelled for " +
                    booking.getEmployee().getEmpName());

        } catch (BookingNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void bookCab(String empId, int cabChoice) {
        System.out.println("Cab booking happens automatically after seat booking.");
    }

    //Show all seat bookings
    @Override
    public void showAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        System.out.println("\n--- All Current Bookings ---");
        bookings.forEach(b ->
                System.out.printf("Employee=%s, Dept=%s, SeatType=%s, SeatID=%d, Food=%s, Date=%s, BookingID=%s%n",
                        b.getEmployee().getEmpName(),
                        b.getEmployee().getDepartment(),
                        b.getSeat().getSeatType(),
                        b.getSeat().getSeatId(),
                        b.getFoodOption(),
                        b.getBookingDate().format(formatter),
                        b.getBookingId())
        );
        System.out.println("------------------------------------------------------------");
    }

    //Show all cab bookings
    @Override
    public void showAllCabBookings() {
        if (cabBookings.isEmpty()) {
            System.out.println("No cab bookings yet!");
            return;
        }

        System.out.println("\n--- All Cab Bookings ---");
        cabBookings.forEach(b ->
                System.out.printf("Employee=%s, Route=%s, Pickup=%s, Date=%s, CabBookingID=%s%n",
                        b.employee().getEmpName(),
                        b.route(),
                        b.pickupPoint(),
                        b.date(),
                        b.cabBookingId())
        );
        System.out.println("------------------------------------------------------------");
    }

    //Varargs
    public void printBookings(Booking... list) {
        for (Booking b : list) System.out.println(b);
    }
}
