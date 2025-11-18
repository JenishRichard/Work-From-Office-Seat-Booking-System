package model;

import java.time.LocalDate;

//Represents a cab booking made after seat booking.
//Implemented as a record - immutable
public record CabBooking(
        String cabBookingId,
        Employee employee,
        Route route,
        PickupPoint pickupPoint,
        LocalDate date
) {}
