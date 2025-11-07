import java.util.*;

class Reservation {
    int id;
    String user;
    int slotId;
    boolean paid;

    Reservation(int id, String user, int slotId) {
        this.id = id;
        this.user = user;
        this.slotId = slotId;
        this.paid = false;
    }
}

public class SmartParkingApp {
    // basic slot statuses: "FREE", "RESERVED", "OCCUPIED"
    static String[] slots = { "FREE", "FREE", "FREE", "FREE", "FREE", "FREE" };
    static int[] price = {50,50,80,50,80,50};
    static HashMap<String,String> users = new HashMap<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int nextReservationId = 1001;

    public static void main(String[] args) {
        // default user for testing
        users.put("user", "1234");
        System.out.println("=== SIMPLE PARKING APP ===");

        mainLoop();
    }

    static void mainLoop() {
        while (true) {
            System.out.print("\nAre you a NEW user? (y/n) or type exit: ");
            String ans = sc.nextLine().trim();
            if (ans.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }
            if (ans.equalsIgnoreCase("y")) {
                registerUser();
            } else if (ans.equalsIgnoreCase("n")) {
                loginFlow();
            } else {
                System.out.println("Please enter 'yes(y)' or 'no(n)' (or exit).");
            }
        }
    }

    static void registerUser() {
        System.out.print("Choose username: ");
        String u = sc.nextLine().trim();
        if (users.containsKey(u)) {
            System.out.println("Username exists. Try a different one.");
            return;
        }
        System.out.print("Choose password: ");
        String p = sc.nextLine().trim();
        users.put(u, p);
        System.out.println("User created. Now you can login.");
    }

    static void loginFlow() {
        System.out.print("Login as (user/admin): ");
        String type = sc.nextLine().trim();
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine().trim();

        if (type.equalsIgnoreCase("admin")) {
            if (u.equals("admin") && p.equals("admin123")) {
                adminMenu();
            } else {
                System.out.println("Invalid admin credentials.");
            }
        } else if (type.equalsIgnoreCase("user")) {
            if (users.containsKey(u) && users.get(u).equals(p)) {
                userMenu(u);
            } else {
                System.out.println("Invalid user credentials.");
            }
        } else {
            System.out.println("Type must be 'user' or 'admin'.");
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View users");
            System.out.println("2. View slots");
            System.out.println("3. View reservations");
            System.out.println("4. Back to main");
            System.out.print("Choose: ");
            int c = safeInt();
            if (c == 1) {
                System.out.println("Users:");
                for (String u : users.keySet()) System.out.println("- " + u);
            } else if (c == 2) {
                showSlots();
            } else if (c == 3) {
                showReservations();
            } else if (c == 4) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    static void userMenu(String username) {
        while (true) {
            System.out.println("\n--- USER MENU (" + username + ") ---");
            System.out.println("1. View available slots");
            System.out.println("2. Reserve a slot");
            System.out.println("3. Pay for reservation");
            System.out.println("4. Start parking (must be paid)");
            System.out.println("5. End parking (give slot id)");
            System.out.println("6. Logout to main");
            System.out.print("Choose: ");
            int c = safeInt();
            if (c == 1) {
                showSlots();
            } else if (c == 2) {
                reserveSlot(username);
            } else if (c == 3) {
                payReservation(username);
            } else if (c == 4) {
                startWithReservation(username);
            } else if (c == 5) {
                endParking();
            } else if (c == 6) {
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    // --- basic operations ---
    static void showSlots() {
        System.out.println("\nSlots:");
        for (int i = 0; i < slots.length; i++) {
            System.out.println("Slot " + (i+1) + " - " + slots[i] + " - ₹" + price[i] + "/hr");
        }
    }

    static void reserveSlot(String username) {
        showSlots();
        System.out.print("Enter slot id to reserve: ");
        int id = safeInt();
        if (!validSlot(id)) { System.out.println("Invalid slot."); return; }
        if (!slots[id-1].equals("FREE")) { System.out.println("Slot not free."); return; }
        slots[id-1] = "RESERVED";
        Reservation r = new Reservation(nextReservationId++, username, id);
        reservations.add(r);
        System.out.println("Reserved. Reservation ID: " + r.id + ". Remember it to pay/start.");
    }

    static void payReservation(String username) {
        System.out.print("Enter reservation id to pay: ");
        int rid = safeInt();
        Reservation r = findReservation(rid);
        if (r == null || !r.user.equals(username) || r.paid) {
            System.out.println("No unpaid reservation found for you with that ID.");
            return;
        }
        int slotPrice = price[r.slotId - 1];
        System.out.print("Enter hours to pre-pay: ");
        int hrs = safeInt();
        if (hrs <= 0) { System.out.println("Invalid hours."); return; }
        int total = slotPrice * hrs;
        // we just mark paid (no real payment)
        r.paid = true;
        System.out.println("Paid ₹" + total + " for reservation " + rid + ".");
    }

    static void startWithReservation(String username) {
        System.out.print("Enter reservation id to start session: ");
        int rid = safeInt();
        Reservation r = findReservation(rid);
        if (r == null || !r.user.equals(username)) {
            System.out.println("No such reservation for you.");
            return;
        }
        if (!r.paid) {
            System.out.println("You must pay before starting.");
            return;
        }
        if (!slots[r.slotId - 1].equals("RESERVED")) {
            System.out.println("Slot is no longer reserved. Cannot start.");
            return;
        }
        slots[r.slotId - 1] = "OCCUPIED";
        // mark reservation as used by clearing it (basic)
        reservations.remove(r);
        System.out.println("Session started on slot " + r.slotId + ".");
    }

    static void endParking() {
        System.out.print("Enter slot id to end session: ");
        int id = safeInt();
        if (!validSlot(id)) { System.out.println("Invalid slot."); return; }
        if (!slots[id-1].equals("OCCUPIED")) { System.out.println("Slot not occupied."); return; }
        slots[id-1] = "FREE";
        System.out.println("Session ended. Slot " + id + " is now FREE.");
    }

    // --- helpers ---
    static boolean validSlot(int id) {
        return id >=1 && id <= slots.length;
    }

    static Reservation findReservation(int rid) {
        for (Reservation r : reservations) if (r.id == rid) return r;
        return null;
    }

    static void showReservations() {
        System.out.println("\nReservations:");
        for (Reservation r : reservations) {
            System.out.println("RID:" + r.id + " User:" + r.user + " Slot:" + r.slotId + " Paid:" + r.paid);
        }
    }

    static int safeInt() {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Enter a number: ");
            }
        }
    }
}
