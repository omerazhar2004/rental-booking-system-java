import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles room searching with filtering (city/postcode, price, availability)
 * and sorting via a pluggable SortStrategy.
 */

public class SearchRoom {

    private final IRoomRepository roomRepo;
    private final SortStrategy sortStrategy;

    public SearchRoom(IRoomRepository roomRepo, SortStrategy sortStrategy) {
        this.roomRepo = Objects.requireNonNull(roomRepo, "roomRepo cannot be null");
        this.sortStrategy = Objects.requireNonNull(sortStrategy, "sortStrategy cannot be null");
    }

    public List<Room> search(
            String cityOrNull,
            String postcodeOrNull,
            DateRange requestedRange,
            int weeks,
            int minPrice,
            int maxPrice
    ) {
        Objects.requireNonNull(requestedRange, "requestedRange cannot be null");

        List<Room> eligible = new ArrayList<>();

        for (Room room : roomRepo.findAll()) {

            // City/Postcode matching
            boolean cityProvided = cityOrNull != null && !cityOrNull.isBlank();
            boolean postcodeProvided = postcodeOrNull != null && !postcodeOrNull.isBlank();

            boolean matchCity = !cityProvided || room.getCity().equalsIgnoreCase(cityOrNull.trim());

            boolean matchPostcode = !postcodeProvided ||
                    (room.getPostcode() != null && room.getPostcode().equalsIgnoreCase(postcodeOrNull.trim()));

            // If both are provided, accept a room that matches EITHER
            if (cityProvided && postcodeProvided) {
                if (!(matchCity || matchPostcode)) continue;
            } else {
                // If only one is provided, enforce it
                if (!matchCity) continue;
                if (!matchPostcode) continue;
            }

            // Price filters
            if (minPrice > 0 && room.getWeeklyPrice() < minPrice) continue;
            if (maxPrice > 0 && room.getWeeklyPrice() > maxPrice) continue;

            // Eligibility: availability + min/max stay
            if (room.isAvailable(requestedRange, weeks)) {
                eligible.add(room);
            }
        }

        return sortStrategy.sort(eligible);
    }

  
    public List<Room> search(String city, DateRange requestedRange, int weeks, int minPrice, int maxPrice) {
        return search(city, null, requestedRange, weeks, minPrice, maxPrice);
    }
}
