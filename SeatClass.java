import java.util.Scanner;

// Parent class
class SeatClass {
    double multiplier;

    SeatClass(double multiplier) {
        this.multiplier = multiplier;
    }

    double calculatePrice(double basePrice) {
        return basePrice * multiplier;
    }
}

// Child classes - Inheritance and Method Overriding
class Premium extends SeatClass {
    Premium() {
        super(1.5);
    }

    @Override
    double calculatePrice(double basePrice) {
        return basePrice * multiplier;
    }
}

class Standard extends SeatClass {
    Standard() {
        super(1.2);
    }

    @Override
    double calculatePrice(double basePrice) {
        return basePrice * multiplier;
    }
}

class Economy extends SeatClass {
    Economy() {
        super(1.0);
    }

    @Override
    double calculatePrice(double basePrice) {
        return basePrice * multiplier;
    }
}

// Main class
public class Main {

    static Scanner sc = new Scanner(System.in);

    // Datatypes and Arrays
    static boolean[][] seats = new boolean[3][5];
    static String[][] bookingIds = new String[3][5];

    static double basePrice = 200.0;
    static int totalBookings = 0;
    static double totalRevenue = 0;

    // Method to display seats
    static void displaySeats() {
        System.out.println("\n----- CINEMA SEAT MAP -----");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (seats[i][j]) {
                    System.out.print("[XX] ");
                } else {
                    System.out.print("[" + (char)('A' + i)
                            + (j + 1) + "] ");
                }
            }
            System.out.println();
        }
        System.out.println("XX = Booked");
    }

    // Method overloading - Search using row and column
    static boolean searchSeat(int row, int col) {
        return seats[row][col];
    }

    // Method overloading - Search using seat ID
    static boolean searchSeat(String seatId) {
        int row = seatId.charAt(0) - 'A';
        int col = Integer.parseInt(seatId.substring(1)) - 1;

        return searchSeat(row, col);
    }

    // Method to calculate show time multiplier
    static double getTimeMultiplier(int time) {
        if (time >= 6) {
            return 1.2;
        } else {
            return 1.0;
        }
    }

    // Method to book a seat
    static void bookSeat() {
        displaySeats();

        System.out.print("\nEnter seat ID (A1 to C5): ");
        String seatId = sc.nextLine().toUpperCase();

        try {
            if (!seatId.matches("[A-C][1-5]")) {
                throw new IllegalArgumentException("Invalid seat ID!");
            }

            if (searchSeat(seatId)) {
                throw new Exception("Seat is already booked!");
            }

            int row = seatId.charAt(0) - 'A';
            int col = Integer.parseInt(seatId.substring(1)) - 1;

            // Inheritance and polymorphism
            SeatClass seatClass;

            if (row == 0) {
                seatClass = new Premium();
            } else if (row == 1) {
                seatClass = new Standard();
            } else {
                seatClass = new Economy();
            }

            System.out.println("\n1. Morning Show");
            System.out.println("2. Evening Show");
            System.out.print("Choose show time: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice != 1 && choice != 2) {
                throw new IllegalArgumentException("Invalid show time!");
            }

            int time = (choice == 1) ? 10 : 7;

            double timeMultiplier = getTimeMultiplier(time);

            double finalPrice =
                    seatClass.calculatePrice(basePrice) * timeMultiplier;

            seats[row][col] = true;

            String bookingId = "BK" + (++totalBookings);
            bookingIds[row][col] = bookingId;

            totalRevenue += finalPrice;

            System.out.println("\n----- BOOKING CONFIRMED -----");
            System.out.println("Seat ID: " + seatId);
            System.out.println("Booking ID: " + bookingId);
            System.out.println("Base Price: Rs." + basePrice);
            System.out.println("Final Price: Rs." + finalPrice);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to cancel booking
    static void cancelBooking() {
        System.out.print("\nEnter seat ID to cancel: ");
        String seatId = sc.nextLine().toUpperCase();

        try {
            if (!seatId.matches("[A-C][1-5]")) {
                throw new IllegalArgumentException("Invalid seat ID!");
            }

            if (!searchSeat(seatId)) {
                throw new Exception("Seat is not booked!");
            }

            int row = seatId.charAt(0) - 'A';
            int col = Integer.parseInt(seatId.substring(1)) - 1;

            seats[row][col] = false;

            System.out.println("Booking " + bookingIds[row][col]
                    + " cancelled successfully!");

            bookingIds[row][col] = null;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to display report
    static void showReport() {
        int booked = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (seats[i][j]) {
                    booked++;
                }
            }
        }

        System.out.println("\n----- CINEMA REPORT -----");
        System.out.println("Total Seats: 15");
        System.out.println("Booked Seats: " + booked);
        System.out.println("Available Seats: " + (15 - booked));
        System.out.println("Total Revenue: Rs." + totalRevenue);
    }

    // Main method
    public static void main(String[] args) {

        int choice;

        // While loop
        while (true) {

            System.out.println("\n===== CINEMA SEAT BOOKING SYSTEM =====");
            System.out.println("1. View Seats");
            System.out.println("2. Book Seat");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Report");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                // Switch statement
                switch (choice) {

                    case 1:
                        displaySeats();
                        break;

                    case 2:
                        bookSeat();
                        break;

                    case 3:
                        cancelBooking();
                        break;

                    case 4:
                        showReport();
                        break;

                    case 5:
                        System.out.println("Thank you for using our system!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}