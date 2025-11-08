package model;

import java.time.LocalDate;

public class Booking {
	private String bookingId;
    private Employee employee;
    private Seat seat;
    private LocalDate date;
    private BookingStatus status;
    private FoodOption foodOption;

    public Booking(String bookingId, Employee employee, Seat seat,
                   LocalDate date, BookingStatus status, FoodOption foodOption) {
        this.bookingId = bookingId;
        this.employee = employee;
        this.seat = seat;
        this.date = date;
        this.status = status;
        this.foodOption = foodOption;
    }

    public String getBookingId() { return bookingId; }
    public Employee getEmployee() { return employee; }
    public Seat getSeat() { return seat; }
    public LocalDate getDate() { return date; }
    public BookingStatus getStatus() { return status; }
    public FoodOption getFoodOption() { return foodOption; }

    @Override
    public String toString() {
    	return "Booking[ID=" + bookingId + ", Emp=" + employee.getEmpName() +
    	           ", Seat=" + seat.getSeatType() + ", Food=" + foodOption + "]";
    }
}
