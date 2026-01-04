import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceAscendingSort implements SortStrategy {

    @Override
    public List<Room> sort(List<Room> rooms) {
        List<Room> copy = new ArrayList<>(rooms);
        copy.sort(Comparator.comparingInt(Room::getWeeklyPrice));
        return copy;
    }
}
