
import java.time.LocalDate;
import java.util.Objects;

public class Booking {

    private final int bookingId;
    private final Room room;
    private final DateRange dateRange;

    private BookingStatus status = BookingStatus.REQUESTED;
    private boolean depositPaid = false;
    private final LocalDate dateOfBooking;

    public Booking(int bookingId, Room room, DateRange dateRange) {
        if (bookingId <= 0) throw new IllegalArgumentException("bookingId must be positive");
        this.bookingId = bookingId;
        this.room = Objects.requireNonNull(room, "room cannot be null");
        this.dateRange = Objects.requireNonNull(dateRange, "dateRange cannot be null");
        this.dateOfBooking = LocalDate.now();
    }

    public int getBookingId() { return bookingId; }
    public Room getRoom() { return room; }
    public DateRange getDateRange() { return dateRange; }
    public BookingStatus getStatus() { return status; }
    public boolean isDepositPaid() { return depositPaid; }
    public LocalDate getDateOfBooking() { return dateOfBooking; }

    public void requestBooking() {
        if (status != BookingStatus.REQUESTED) {
            throw new IllegalStateException("Booking can only be requested once.");
        }
    }

    public void acceptBooking() {
        if (status != BookingStatus.REQUESTED) {
            throw new IllegalStateException("Can only accept a REQUESTED booking.");
        }
        status = BookingStatus.ACCEPTED;
    }

    public void confirmDeposit() {
        if (status != BookingStatus.ACCEPTED) {
            throw new IllegalStateException("Deposit can only be paid after ACCEPTED.");
        }
        depositPaid = true;
        status = BookingStatus.CONFIRMED;
    }

    public void completeBooking() {
        if (status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Can only complete a CONFIRMED booking.");
        }
        status = BookingStatus.COMPLETED;
    }
}

