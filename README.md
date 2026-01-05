# Student Rentals — Scripted Demo

This project is a small Java prototype for a student room rental system.  
It demonstrates core features such as room listings, searching with eligibility rules, booking lifecycle, deposit confirmation, double-booking prevention, and reviews.

The entry point is a **scripted demo** that seeds sample data and prints a walkthrough of the main requirements.

---

## Features Demonstrated

- Create accounts for **Student** and **Host**
- Host creates a **Listing** and multiple **Rooms**
- Host sets room details: weekly price, deposit, min/max stay, availability ranges
- Student searches rooms by:
  - **City** or **Postcode**
  - **Date range**
  - **Price range**
- Search results are filtered to eligible rooms only (availability + min/max stay)
- Results are sorted by weekly price (ascending)
- Booking lifecycle:
  - REQUESTED → ACCEPTED → CONFIRMED → COMPLETED
  - REQUESTED → REJECTED
- Deposit confirmation prevents double-booking by rejecting overlapping **CONFIRMED** bookings
- Student can leave a review only after booking completion

---

## How to Run

### Requirements
- Java (JDK) installed (Java 17+ recommended)
- Command line / PowerShell

### Compile
From the folder containing the `.java` files:

```bash
javac *.java

java StudentRentalsApp

