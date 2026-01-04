import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryRoomRepository implements IRoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms); // protect internal list
    }

    @Override
    public void add(Room room) {
        rooms.add(Objects.requireNonNull(room, "room cannot be null"));
    }
}
