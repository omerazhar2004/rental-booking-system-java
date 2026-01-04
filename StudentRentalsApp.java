

import java.time.LocalDate;
import java.util.List;


/**
 * Purpose:
 * This class is used to demonstrate the booking cycle of searching, booking, state changes and output
 * it is meant to seed data inorder to demonstrate the functionality of Student Rentals.
 * It is meant to run a scripted demo of the Student Rentals.
 * It contains the main method for implementing all the features.
 * It has been kept minimum on purpose as it delegates the bussiness logic to domain classes.
 */

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

        System.out.println("Date range overlap test(should be true): " + a.overlaps(b)); // true
        System.out.println("Date range overlap test(should be false): " + a.overlaps(c)); // false

        System.out.println("Reached Room test");

        Room room = new Room(1, 150, 2, 12, "Cardiff", "CF10");
        room.addAvailabilityRange(new DateRange(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 3, 1)
        ));

        DateRange request = new DateRange(
                LocalDate.of(2026, 1, 10),
                LocalDate.of(2026, 2, 7)
        );

        System.out.println("Room available for 4 weeks(should be true): " + room.isAvailable(request, 4)); // true
        System.out.println("Room available for 1 weeks(should be false): " + room.isAvailable(request, 1)); // false

        Booking booking = new Booking(1, room, request);
        System.out.println("Booking Lifecycle:");
        System.out.println("Status: " + booking.getStatus()); // REQUESTED
        booking.acceptBooking();
        System.out.println("Status: " + booking.getStatus()); // ACCEPTED
        booking.confirmDeposit();
        System.out.println("Status: " + booking.getStatus()); // CONFIRMED
        booking.completeBooking();
        System.out.println("Status: " + booking.getStatus()); // COMPLETED

        IRoomRepository repo = new InMemoryRoomRepository();
        repo.add(room);

        SearchRoom search = new SearchRoom(repo, new PriceAscendingSort());

        List<Room> results = search.search("Cardiff", request, 4, 0, 1000);
        System.out.println("Results in Cardiff: " + results.size()); // should be 1
        System.out.println("Results in London: " + search.search("London", request, 4, 0, 1000).size());


    }
}
