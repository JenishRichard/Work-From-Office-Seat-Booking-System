package model;

import java.time.LocalDate;

//Represents a single seat booking made by an employee.
public final class Booking {

    private final String bookingId;
    private final Employee employee; //Defensive copy used to maintain immutability
    private final Seat seat;
    private final LocalDate bookingDate;
    private final BookingStatus status;
    private final FoodOption foodOption;

    public Booking(String bookingId, Employee employee, Seat seat,
                   LocalDate bookingDate, BookingStatus status, FoodOption foodOption) {

        this.bookingId = bookingId;
        this.employee = new Employee(employee); //Defensive copy
        this.seat = seat;
        this.bookingDate = bookingDate;
        this.status = status;
        this.foodOption = foodOption;
    }

    public String getBookingId() { return bookingId; }
    public Employee getEmployee() { return new Employee(employee); } //Another defensive copy
    public Seat getSeat() { return seat; }
    public LocalDate getBookingDate() { return bookingDate; }
    public BookingStatus getStatus() { return status; }
    public FoodOption getFoodOption() { return foodOption; }

    @Override
    public String toString() {
        return "Booking[ID=" + bookingId +
                ", Emp=" + employee.getEmpName() +
                ", Seat=" + seat.getSeatType() +
                ", Food=" + foodOption + "]";
    }
}
