import java.util.List;

public interface IRoomRepository {
    List<Room> findAll();
    void add(Room room);
}

