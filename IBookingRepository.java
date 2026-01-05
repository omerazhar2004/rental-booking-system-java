import java.util.List;

public interface IBookingRepository {
    void add(Booking booking);
    Booking findById(int bookingId);
    List<Booking> findByRoomId(int roomId);
}
