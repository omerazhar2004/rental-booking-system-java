
import java.time.LocalDate;
import java.util.Objects;


/**
 * This class covers the complete booking cycle and deals with the status of the Booking of rooms.
 * REQUESTED: Student has requested to book room
 * ACCEPTED: Host has accepted the students request for room.
 * CONFIRMED: Student has paid relevant deposit for room
 * COMPLETED: Student has completed stay at room.
 * 
 */
public class Booking {
 // encapsulated booking data
    private final int bookingId;
    private final Room room;
    private final DateRange dateRange;

    private BookingStatus status = BookingStatus.REQUESTED;
    private boolean depositPaid = false;
    private final LocalDate dateOfBooking;

    /**
     * This method validates input and establishes booking invarients at construction time.
     * An IllegalArgumentException is thrown if bookingID is negative or value of room or dateRange is null etc.
     */
    public Booking(int bookingId, Room room, DateRange dateRange) {
        if (bookingId <= 0) throw new IllegalArgumentException("bookingId must be positive");
        this.bookingId = bookingId;
        this.room = Objects.requireNonNull(room, "room cannot be null");
        this.dateRange = Objects.requireNonNull(dateRange, "dateRange cannot be null");
        this.dateOfBooking = LocalDate.now();
    }

    //getter methods
    public int getBookingId() { return bookingId; }
    public Room getRoom() { return room; }
    public DateRange getDateRange() { return dateRange; }
    public BookingStatus getStatus() { return status; }
    public boolean isDepositPaid() { return depositPaid; }
    public LocalDate getDateOfBooking() { return dateOfBooking; }


    /**
     * Validates that Booking is in the REQUESTED state.
     * It therefore ensures that the same student does not make multiple requests to book the same room.
     */
    public void requestBooking() {
        if (status != BookingStatus.REQUESTED) {
            throw new IllegalStateException("Booking can only be requested once.");
        }
    }

    
    /**
     * Ensures that the status of booking is set to ACCEPTED only if its status is already REQUESTED.
     * It therefore prevents the host from accepting a booking which was never request by any student in the first place.
     */
    public void acceptBooking() {
        if (status != BookingStatus.REQUESTED) {
            throw new IllegalStateException("Can only accept a REQUESTED booking.");
        }
        status = BookingStatus.ACCEPTED;
    }

    /**
     * Ensures that the status of a room is only set to CONFIRMED if the student pays the deposit for it.
     * It throws an IllegalStateException if attempts are made to pay deposit for a room whos booking status is not yet set to
     * ACCEPTED by the host.
     */
    public void confirmDeposit() {
        if (status != BookingStatus.ACCEPTED) {
            throw new IllegalStateException("Deposit can only be paid after ACCEPTED.");
        }
        depositPaid = true;
        status = BookingStatus.CONFIRMED;
    }

    /**
     * Ensures that the status of a booking is only set to COMPLETED if its status earlier was set to CONFIRMED.
     * A status of a booking is only set to CONFIRMED once student pays the deposit for relevant room.
     */
    public void completeBooking() {
        if (status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Can only complete a CONFIRMED booking.");
        }
        status = BookingStatus.COMPLETED;
    }
    public void declineBooking() {
    if (status != BookingStatus.REQUESTED) {
        throw new IllegalStateException("Can only reject a REQUESTED booking.");
    }
    status = BookingStatus.REJECTED;
}

}

