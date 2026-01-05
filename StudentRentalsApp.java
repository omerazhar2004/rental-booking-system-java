import java.time.LocalDate;
import java.util.List;

/**
 * Scripted demo entry point for: search (city/postcode), booking lifecycle, deposit confirmation,
 * double-booking prevention, and reviews after completion.
 */

public class StudentRentalsApp {

    public static void main(String[] args) {

        System.out.println("=== STUDENT RENTALS: SCRIPTED DEMO START ===");

        // 1) Accounts
        Student student = new Student(1, "Alice Student", "alice@student.com");
        Host host = new Host(1, "Bob Host", "bob@host.com");
        System.out.printf("[1] Accounts created -> Student: %s, Host: %s%n", student.getName(), host.getName());

        // 2) Listing + Rooms
        Listing listing = new Listing(1, host, "Cardiff CF10");

        Room room1 = new Room(1, 200, 4, 12, "Cardiff", "CF10");
        room1.setDeposit(300);
        room1.addAvailabilityRange(new DateRange(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 1)));

        Room room2 = new Room(2, 150, 4, 12, "Cardiff", "CF10");
        room2.setDeposit(250);
        room2.addAvailabilityRange(new DateRange(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 1)));

        Room room3 = new Room(3, 180, 6, 12, "Cardiff", "CF24");
        room3.setDeposit(275);
        // Availability is [2026-01-15 to 2026-02-28) (end exclusive)
        room3.addAvailabilityRange(new DateRange(LocalDate.of(2026, 1, 15), LocalDate.of(2026, 2, 28)));

        listing.addRoom(room1);
        listing.addRoom(room2);
        listing.addRoom(room3);

        System.out.println("[2] Host created listing and rooms:");
        System.out.println("    Listing address: Cardiff CF10");

        System.out.printf("    Room %d -> city=%s, postcode=%s, weeklyPrice=£%d, deposit=£%d, minWeeks=%d, maxWeeks=%d%n",
                room1.getRoomId(), room1.getCity(), room1.getPostcode(), room1.getWeeklyPrice(), room1.getDeposit(),
                room1.getMinWeeks(), room1.getMaxWeeks());

        System.out.printf("    Room %d -> city=%s, postcode=%s, weeklyPrice=£%d, deposit=£%d, minWeeks=%d, maxWeeks=%d%n",
                room2.getRoomId(), room2.getCity(), room2.getPostcode(), room2.getWeeklyPrice(), room2.getDeposit(),
                room2.getMinWeeks(), room2.getMaxWeeks());

        System.out.printf("    Room %d -> city=%s, postcode=%s, weeklyPrice=£%d, deposit=£%d, minWeeks=%d, maxWeeks=%d%n",
                room3.getRoomId(), room3.getCity(), room3.getPostcode(), room3.getWeeklyPrice(), room3.getDeposit(),
                room3.getMinWeeks(), room3.getMaxWeeks());

        // Repos + service
        IRoomRepository roomRepo = new InMemoryRoomRepository();
        roomRepo.add(room1);
        roomRepo.add(room2);
        roomRepo.add(room3);

        IBookingRepository bookingRepo = new InMemoryBookingRepository();
        StudentRentalsService service = new StudentRentalsService(roomRepo, bookingRepo);

        SearchRoom search = new SearchRoom(roomRepo, new PriceAscendingSort());

        // 3-5) City search + filter + sort
        DateRange requested = new DateRange(LocalDate.of(2026, 1, 20), LocalDate.of(2026, 2, 17));
        int weeks = 4;
        int minPrice = 0;
        int maxPrice = 1000;

        System.out.printf("[3-5] Student searches rooms -> city=Cardiff, dateRange=%s, weeks=%d, priceRange=£%d-£%d%n",
                requested, weeks, minPrice, maxPrice);
        System.out.println("      (Results filtered by eligibility and sorted by weekly price ascending)");

        List<Room> results = search.search("Cardiff", null, requested, weeks, minPrice, maxPrice);

        System.out.printf("      Results count: %d%n", results.size());
        for (Room r : results) {
            System.out.printf("      -> Room %d | postcode=%s | weeklyPrice=£%d | deposit=£%d | minWeeks=%d, maxWeeks=%d%n",
                    r.getRoomId(), r.getPostcode(), r.getWeeklyPrice(), r.getDeposit(), r.getMinWeeks(), r.getMaxWeeks());
        }

        // 3-5) Postcode search demo (must match Room 3 minWeeks=6 and fit within availability)
        int weeksForPostcodeDemo = 6;
        DateRange requestedForPostcodeDemo = new DateRange(
                LocalDate.of(2026, 1, 20),
                LocalDate.of(2026, 2, 28) // end exclusive; fits room3 availability end
        );

        System.out.printf("[3-5] Student searches rooms -> postcode=CF24, dateRange=%s, weeks=%d, priceRange=£%d-£%d%n",
                requestedForPostcodeDemo, weeksForPostcodeDemo, minPrice, maxPrice);
        System.out.println("      (Postcode search + eligibility filtering)");

        List<Room> postcodeResults = search.search(null, "CF24", requestedForPostcodeDemo, weeksForPostcodeDemo, minPrice, maxPrice);

        System.out.printf("      Results count: %d%n", postcodeResults.size());
        for (Room r : postcodeResults) {
            System.out.printf("      -> Room %d | postcode=%s | weeklyPrice=£%d | deposit=£%d | minWeeks=%d, maxWeeks=%d%n",
                    r.getRoomId(), r.getPostcode(), r.getWeeklyPrice(), r.getDeposit(), r.getMinWeeks(), r.getMaxWeeks());
        }

        // 6-8) Booking: request -> accept -> deposit confirm
        System.out.println("[6] Student requests booking for Room 2...");
        Booking booking1 = service.requestBooking(1, student, room2, requested);
        System.out.printf("    Booking 1 status: %s%n", booking1.getStatus());

        System.out.println("[7] Host accepts Booking 1...");
        service.acceptBooking(1);
        System.out.printf("    Booking 1 status: %s%n", booking1.getStatus());

        System.out.println("[8] Student pays deposit to confirm Booking 1...");
        service.confirmDeposit(1);
        System.out.printf("    Booking 1 status: %s%n", booking1.getStatus());

        // 7) Reject path
        System.out.println("[7] Student requests another booking (Room 1) then Host rejects it...");
        Booking booking99 = service.requestBooking(99, student, room1, requested);
        System.out.printf("    Booking 99 status: %s%n", booking99.getStatus());
        service.rejectBooking(99);
        System.out.printf("    Booking 99 status: %s%n", booking99.getStatus());

        // 9) Double-booking prevention
        System.out.println("[9] Double-booking test: attempt to confirm an overlapping booking on Room 2...");
        Booking booking2 = service.requestBooking(2, student, room2, requested);
        service.acceptBooking(2);

        try {
            service.confirmDeposit(2);
            System.out.println("    ERROR: booking2 was confirmed (this should not happen).");
        } catch (Exception e) {
            System.out.println("    Double booking prevented OK -> " + e.getMessage());
        }

        // 10) Complete
        System.out.println("[10] Completing Booking 1...");
        service.completeBooking(1);
        System.out.printf("     Booking 1 status: %s%n", booking1.getStatus());

        // 11) Review
        System.out.println("[11] Student leaves a review after completion...");
        Review review = service.leaveReview(1, booking1, 5, "Great stay. Clean room and friendly host.");
        System.out.println("     Review submitted: " + review);

        System.out.println("=== STUDENT RENTALS: SCRIPTED DEMO END ===");
    }
}
