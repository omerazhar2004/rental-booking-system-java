

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a date range with an inclusive start date
 * and an exclusive end date. It therefore helps to encapsulate all date range logic within the system,
 * including the date range overlap detection as well containement. These algorithms are therefore essential 
 * for correct bookings and for avoiding booking conflicts.
 */
public final class DateRange {

    private final LocalDate start;
    private final LocalDate end;

    /**
     * Validates input ensuring that none of the dates entered are null.
     * Also throws IllegalArgumentException if the starts date entered for a room is accidently set to a date after the end date.
     */
    public DateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        this.start = start;
        this.end = end;
    }

    /**
     *  Helps to detect if two date ranges share atleast one common day.
     * It therefore helps to detect booking conflicts and also prevent double bookings.
     */

    public boolean overlaps(DateRange other) {
        Objects.requireNonNull(other, "Other DateRange cannot be null");
        return start.isBefore(other.end) && other.start.isBefore(end);
    }

    /**
     * Helps to check if the booking request made by a student fits fully within the availability date range set by
     * the relevant host.
     */
    public boolean contains(DateRange other) {
        Objects.requireNonNull(other, "Other DateRange cannot be null");
        return !start.isAfter(other.start) && !end.isBefore(other.end);
    }


    @Override
    public String toString() {
        return "[" + start + " to " + end + ")";
    }
}
