

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a date range with an inclusive start date
 * and an exclusive end date.
 */
public final class DateRange {

    private final LocalDate start;
    private final LocalDate end;

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

    public boolean overlaps(DateRange other) {
        Objects.requireNonNull(other, "Other DateRange cannot be null");
        return start.isBefore(other.end) && other.start.isBefore(end);
    }
    public boolean contains(DateRange other) {
        Objects.requireNonNull(other, "Other DateRange cannot be null");
        return !start.isAfter(other.start) && !end.isBefore(other.end);
    }


    @Override
    public String toString() {
        return "[" + start + " to " + end + ")";
    }
}
