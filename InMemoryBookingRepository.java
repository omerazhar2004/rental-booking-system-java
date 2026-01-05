import java.util.ArrayList;
import java.util.List;

public class InMemoryBookingRepository implements IBookingRepository {

    private final List<Booking> bookings = new ArrayList<>();

    @Override
    public void add(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public Booking findById(int bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId) return b;
        }
        return null;
    }

    @Override
    public List<Booking> findByRoomId(int roomId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getRoom().getRoomId() == roomId) {
                result.add(b);
            }
        }
        return result;
    }
}
