import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchRoom {

    private final IRoomRepository roomRepo;
    private final SortStrategy sortStrategy;

    public SearchRoom(IRoomRepository roomRepo, SortStrategy sortStrategy) {
        this.roomRepo = Objects.requireNonNull(roomRepo);
        this.sortStrategy = Objects.requireNonNull(sortStrategy);
    }

    public List<Room> search(String cityOrNull, DateRange requestedRange, int weeks, int minPrice, int maxPrice) {
        Objects.requireNonNull(requestedRange);

        List<Room> eligible = new ArrayList<>();

        for (Room room : roomRepo.findAll()) {
            if (minPrice > 0 && room.getWeeklyPrice() < minPrice) continue;
            if (maxPrice > 0 && room.getWeeklyPrice() > maxPrice) continue;

         

            if (room.isAvailable(requestedRange, weeks)) {
                eligible.add(room);
            }
        }

        return sortStrategy.sort(eligible);
    }
}
