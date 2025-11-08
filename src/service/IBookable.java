package service;

public interface IBookable {
    void bookSeat(String empId, int seatId);
    void cancelBooking(String bookingId);
    void showAllBookings();
}
