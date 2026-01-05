

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single room that can be rented, including pricing,
 * stay constraints, and host-defined availability.
 */

public class Room {

    private final int roomId;

    private int weeklyPrice;   // GBP per week
    private int minWeeks;
    private int maxWeeks;
    private final String city;
    private final String postcode;
    private int deposit;

    private final List<DateRange> availabilityRanges = new ArrayList<>();

    public Room(int roomId, int weeklyPrice, int minWeeks, int maxWeeks, String city, String postcode) {
        if (roomId <= 0) throw new IllegalArgumentException("roomId must be positive");
        if (weeklyPrice <= 0 ) throw new IllegalArgumentException("weeklyPrice must be positive.");
        if(minWeeks <= 0) throw new IllegalArgumentException("minWeeks must be positive.");
        if(maxWeeks < minWeeks) throw new IllegalArgumentException("maxWeeks must be >= minWeeks.");
        if(city == null || city.isBlank()) throw new IllegalArgumentException("city is required.");
        this.roomId = roomId;
        this.weeklyPrice = weeklyPrice;
        this.minWeeks = minWeeks;
        this.maxWeeks = maxWeeks;
        this.city = city.trim();
        this.postcode = (postcode == null || postcode.isBlank()) ? null : postcode.trim();
        setWeeklyPrice(weeklyPrice);
        setMinMaxStay(minWeeks, maxWeeks);
    }

    public String getCity(){
        return city;
    }
    public String getPostcode(){
        return postcode;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getWeeklyPrice() {
        return weeklyPrice;
    }

    public int getMinWeeks() {
        return minWeeks;
    }

    public int getMaxWeeks() {
        return maxWeeks;
    }

    public List<DateRange> getAvailabilityRanges() {
        return new ArrayList<>(availabilityRanges);
    }

    public void addAvailabilityRange(DateRange range) {
        Objects.requireNonNull(range, "range cannot be null");
        availabilityRanges.add(range);
    }

    public void setWeeklyPrice(int weeklyPrice) {
        if (weeklyPrice <= 0) throw new IllegalArgumentException("weeklyPrice must be positive");
        this.weeklyPrice = weeklyPrice;
    }

    public void setMinMaxStay(int minWeeks, int maxWeeks) {
        if (minWeeks <= 0) throw new IllegalArgumentException("minWeeks must be positive");
        if (maxWeeks < minWeeks) throw new IllegalArgumentException("maxWeeks must be >= minWeeks");
        this.minWeeks = minWeeks;
        this.maxWeeks = maxWeeks;
    }

    /**
     * Returns true if:
     * - weeks is within min/max stay AND
     * - requested range is fully contained in at least one availability range
     */
    public boolean isAvailable(DateRange requested, int weeks) {
        Objects.requireNonNull(requested, "requested range cannot be null");

        if (weeks < minWeeks || weeks > maxWeeks) {
            return false;
        }

        for (DateRange available : availabilityRanges) {
            if (available.contains(requested)) {
                return true;
            }
        }
        return false;
    }

  

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        if (deposit < 0) throw new IllegalArgumentException("Deposit cannot be negative");
        this.deposit = deposit;
    }
}
