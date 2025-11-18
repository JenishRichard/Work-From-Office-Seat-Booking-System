package exceptions;

//Custom checked exception used when a booking ID does not exist
public class BookingNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookingNotFoundException(String message) {
        super(message);
    }
}
