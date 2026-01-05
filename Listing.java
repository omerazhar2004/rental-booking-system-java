import java.util.ArrayList;
import java.util.List;


/**
 * A host-owned listing (address) that groups one or more rooms.
 */

public class Listing {

    private final int listingId;
    private final Host host;
    private final String address;
    private final List<Room> rooms = new ArrayList<>();

    public Listing(int listingId, Host host, String address) {
        if (listingId <= 0) throw new IllegalArgumentException("Invalid listingId");
        this.host = host;
        this.address = address;
        this.listingId = listingId;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public Host getHost() {
        return host;
    }
}
