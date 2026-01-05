import java.util.Objects;

/**
 * Coordinates booking actions and enforces rules that need access to stored bookings.
 * (e.g. preventing overlapping confirmed bookings)
 */

public class StudentRentalsService {

    private final IRoomRepository roomRepo;
    private final IBookingRepository bookingRepo;

    public StudentRentalsService(IRoomRepository roomRepo, IBookingRepository bookingRepo) {
        this.roomRepo = Objects.requireNonNull(roomRepo);
        this.bookingRepo = Objects.requireNonNull(bookingRepo);
    }

    public Booking requestBooking(int bookingId, Student student, Room room, DateRange range) {
        Objects.requireNonNull(student);
        Objects.requireNonNull(room);
        Objects.requireNonNull(range);

        Booking booking = new Booking(bookingId, room, range);
        bookingRepo.add(booking);
        return booking;
    }

    public void acceptBooking(int bookingId) {
        Booking b = requireBooking(bookingId);
        b.acceptBooking();
    }

    public void rejectBooking(int bookingId) {
        Booking b = requireBooking(bookingId);
        b.declineBooking();
    }


    // Only CONFIRMED bookings should block overlaps.
    // We check before confirming deposit to prevent double-booking.

    public void confirmDeposit(int bookingId) {
        Booking booking = requireBooking(bookingId);

        // prevent overlapping CONFIRMED bookings for same room
        for (Booking existing : bookingRepo.findByRoomId(booking.getRoom().getRoomId())) {
            if (existing.getStatus() == BookingStatus.CONFIRMED
                    && existing.getDateRange().overlaps(booking.getDateRange())
                    && existing.getBookingId() != booking.getBookingId()) {
                throw new IllegalStateException("Rejected: overlapping confirmed booking exists");
            }
        }

        booking.confirmDeposit();
    }

    public void completeBooking(int bookingId) {
        requireBooking(bookingId).completeBooking();
    }


    // Reviews are only allowed after the booking is completed.

    public Review leaveReview(int reviewId, Booking booking, int rating, String text) {
        Objects.requireNonNull(booking);
        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new IllegalStateException("Can only review completed bookings");
        }
        return new Review(reviewId, rating, text);
    }

    private Booking requireBooking(int bookingId) {
        Booking b = bookingRepo.findById(bookingId);
        if (b == null) throw new IllegalArgumentException("No booking found with id " + bookingId);
        return b;
    }
}
