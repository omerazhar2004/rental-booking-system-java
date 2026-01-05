import java.util.List;

/**
 * Strategy interface for sorting room search results.
 *
 * This enables different ordering behaviours to be applied without modifying
 * the search logic.
 */



public interface SortStrategy {
    List<Room> sort(List<Room> rooms);
}
