import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Simple in-memory repository used for the demo.
 * Data is not persisted between runs.
 */


public class InMemoryRoomRepository implements IRoomRepository {

    private final List<Room> rooms = new ArrayList<>();
    /**
 * Returns all rooms currently stored in the repository.
 *
 * A defensive copy of the internal list is returned to preserve
 * encapsulation and prevent external modification.
 *
 * @return a list of all stored rooms
 */


    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms); // protect internal list
    }

    /**
 * Adds a room to the repository.
 *
 * @param room the room to add (must not be null)
 * @throws NullPointerException if the room is null
 */

    @Override
    public void add(Room room) {
        rooms.add(Objects.requireNonNull(room, "room cannot be null"));
    }
}
