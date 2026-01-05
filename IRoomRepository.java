import java.util.List;


/**
 * Abstraction for accessing stored Room objects.
 * Allows search logic to remain independent of storage details.
 */


public interface IRoomRepository {
    
    List<Room> findAll();
    void add(Room room);
}

