

import java.time.LocalDate;
import java.util.List;



public class StudentRentalsApp {

    public static void main(String[] args) {

        DateRange a = new DateRange(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 10)
        );

        DateRange b = new DateRange(
                LocalDate.of(2026, 1, 5),
                LocalDate.of(2026, 1, 12)
        );

        DateRange c = new DateRange(
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2026, 1, 15)
        );

        System.out.println(a.overlaps(b)); // true
        System.out.println(a.overlaps(c)); // false

        System.out.println("Reached Room test");

        Room room = new Room(1, 150, 2, 12);
        room.addAvailabilityRange(new DateRange(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 3, 1)
        ));

        DateRange request = new DateRange(
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2026, 2, 7)
        );

        System.out.println(room.isAvailable(request, 4)); // true
        System.out.println(room.isAvailable(request, 1)); // false

        Booking booking = new Booking(1, room, request);
        System.out.println(booking.getStatus()); // REQUESTED
        booking.acceptBooking();
        System.out.println(booking.getStatus()); // ACCEPTED
        booking.confirmDeposit();
        System.out.println(booking.getStatus()); // CONFIRMED
        booking.completeBooking();
        System.out.println(booking.getStatus()); // COMPLETED

        IRoomRepository repo = new InMemoryRoomRepository();
        repo.add(room);

        SearchRoom search = new SearchRoom(repo, new PriceAscendingSort());

        List<Room> results = search.search(null, request, 4, 0, 1000);
        System.out.println("Results: " + results.size()); // should be 1


    }
}
