package model;

import java.time.LocalDate;

public class Booking {
    private String bookingId;
    private Employee employee;
    private Seat seat;
    private LocalDate bookingDate;   
    private BookingStatus status;
    private FoodOption foodOption;

    public Booking(String bookingId, Employee employee, Seat seat,
                   LocalDate bookingDate, BookingStatus status, FoodOption foodOption) {
        this.bookingId = bookingId;
        this.employee = employee;
        this.seat = seat;
        this.bookingDate = bookingDate;  
        this.status = status;
        this.foodOption = foodOption;
    }

    public String getBookingId() { return bookingId; }
    public Employee getEmployee() { return employee; }
    public Seat getSeat() { return seat; }
    public LocalDate getBookingDate() { return bookingDate; }  
    public BookingStatus getStatus() { return status; }
    public FoodOption getFoodOption() { return foodOption; }

    @Override
    public String toString() {
        return "Booking[ID=" + bookingId + ", Emp=" + employee.getEmpName() +
               ", Seat=" + seat.getSeatType() + ", Food=" + foodOption + "]";
    }
    public LocalDate getBookingDate1() {
        return bookingDate;  
    }

}
