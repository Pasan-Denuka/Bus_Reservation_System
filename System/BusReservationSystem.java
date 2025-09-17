import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// Customer class
class Customer {
    String name, mobile, email, city;
    int age;

    public Customer(String name, String mobile, String email, String city, int age) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.age = age;
    }

    public String toString() {
        return name + " (" + mobile + ", " + email + ", " + city + ", Age: " + age + ")";
    }
}

// Customer Registration using Stack (Linked List Implementation)
class CustomerRegistration {
    Stack<Customer> customerStack = new Stack<>();

    public void registerCustomer(String name, String mobile, String email, String city, int age) {
        Customer customer = new Customer(name, mobile, email, city, age);
        customerStack.push(customer);
        System.out.println("Customer registered successfully: " + name);
    }

    public void displayCustomers() {
        if (customerStack.isEmpty()) {
            System.out.println("No registered customers.");
        } else {
            System.out.println("Registered Customers:");
            for (Customer c : customerStack) {
                System.out.println(c);
            }
        }
    }
}

// Bus class
class Bus {
    String busNumber, startPoint, endPoint;
    int totalSeats, availableSeats;
    double fare;
    
    public Bus(String busNumber, int totalSeats, String startPoint, String endPoint, double fare) {
        this.busNumber = busNumber;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.fare = fare;
    }

    public String toString() {
        return busNumber + " (" + startPoint + " -> " + endPoint + ", Seats: " + availableSeats + "/" + totalSeats + 
        ", Fare: " + fare + ")";
    }
}

// Bus Registration using Stack
class BusRegistration {
    Stack<Bus> busStack = new Stack<>();

    public void registerBus(String busNumber, int totalSeats, String startPoint, String endPoint, double fare) {
        Bus bus = new Bus(busNumber, totalSeats, startPoint, endPoint, fare);
        busStack.push(bus);
        System.out.println("Bus registered successfully: " + busNumber);
    }

    public void displayBuses() {
        if (busStack.isEmpty()) {
            System.out.println("No registered buses.");
        } else {
            System.out.println("Registered Buses:");
            for (Bus b : busStack) {
                System.out.println(b);
            }
        }
    }
}

// Reservation class
class Reservation {
    Customer customer;
    Bus bus;
    int seatNumber;

    public Reservation(Customer customer, Bus bus, int seatNumber) {
        this.customer = customer;
        this.bus = bus;
        this.seatNumber = seatNumber;
    }

    public String toString() {
        return "Seat " + seatNumber + " reserved for " + customer.name + " on bus " + bus.busNumber;
    }
}

// Bus Seat Reservation using Queue & Linked List
class BusSeatReservation {
    LinkedList<Reservation> reservations = new LinkedList<>();
    Queue<Customer> waitingQueue = new LinkedList<>();

    public void reserveSeat(Customer customer, Bus bus) {
        if (bus.availableSeats > 0) {
            int seatNumber = bus.totalSeats - bus.availableSeats + 1;
            Reservation res = new Reservation(customer, bus, seatNumber);
            reservations.add(res);
            bus.availableSeats--;
            System.out.println(" Seat " + seatNumber + " reserved for " + customer.name + " on bus " + bus.busNumber);
        } else {
            waitingQueue.add(customer);
            System.out.println("No available seats. " + customer.name + " added to waiting list.");
        }
    }

    public void cancelReservation(Customer customer, Bus bus) {
        Reservation toRemove = null;
        for (Reservation res : reservations) {
            if (res.customer.name.equals(customer.name) && res.bus.busNumber.equals(bus.busNumber)) {
                toRemove = res;
                break;
            }
        }

        if (toRemove != null) {
            reservations.remove(toRemove);
            bus.availableSeats++;
            System.out.println(" Reservation cancelled for " + customer.name + " on bus " + bus.busNumber);

            // Assign seat to next waiting customer
            if (!waitingQueue.isEmpty()) {
                Customer nextCustomer = waitingQueue.poll();
                reserveSeat(nextCustomer, bus);
                System.out.println(" Seat allocated to next customer in queue: " + nextCustomer.name);
            }
        } else {
            System.out.println(" Reservation not found.");
        }
    }

    public void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println(" No reservations made.");
        } else {
            System.out.println(" Current Reservations:");
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }
}

// Main class
public class BusReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerRegistration customerReg = new CustomerRegistration();
        BusRegistration busReg = new BusRegistration();
        BusSeatReservation seatRes = new BusSeatReservation();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Bus> buses = new ArrayList<>();

        while (true) {
            System.out.println("\n1. Register Customer\n2. Register Bus\n3. Display Customers\n4. Display Buses\n5. Reserve Seat\n6. Cancel Reservation\n7. Display Reservations\n8. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Register Customer
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Mobile: ");
                    String mobile = scanner.next();
                    System.out.print("Enter Email: ");
                    String email = scanner.next();
                    System.out.print("Enter City: ");
                    String city = scanner.next();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    Customer newCustomer = new Customer(name, mobile, email, city, age);
                    customers.add(newCustomer);
                    customerReg.registerCustomer(name, mobile, email, city, age);
                    break;

                case 2: // Register Bus
                    System.out.print("Enter Bus Number: ");
                    String busNumber = scanner.next();
                    System.out.print("Enter Total Seats: ");
                    int totalSeats = scanner.nextInt();
                    System.out.print("Enter Starting Point: ");
                    String startPoint = scanner.next();
                    System.out.print("Enter Ending Point: ");
                    String endPoint = scanner.next();
                    System.out.print("Enter Fare: ");
                    double fare = scanner.nextDouble();
                    Bus newBus = new Bus(busNumber, totalSeats, startPoint, endPoint, fare);
                    buses.add(newBus);
                    busReg.registerBus(busNumber, totalSeats, startPoint, endPoint, fare);
                    break;

                case 3: // Display Customers
                    customerReg.displayCustomers();
                    break;

                case 4: // Display Buses
                    busReg.displayBuses();
                    break;

                case 5: // Reserve Seat
                    if (customers.isEmpty() || buses.isEmpty()) {
                        System.out.println(" No registered customers or buses.");
                        break;
                    }
                    System.out.println("Select Customer: ");
                    for (int i = 0; i < customers.size(); i++) {
                        System.out.println((i + 1) + ". " + customers.get(i).name);
                    }
                    int custIndex = scanner.nextInt() - 1;

                    System.out.println("Select Bus: ");
                    for (int i = 0; i < buses.size(); i++) {
                        System.out.println((i + 1) + ". " + buses.get(i).busNumber);
                    }
                    int busIndex = scanner.nextInt() - 1;

                    if (custIndex >= 0 && custIndex < customers.size() && busIndex >= 0 && busIndex < buses.size()) {
                        seatRes.reserveSeat(customers.get(custIndex), buses.get(busIndex));
                    } else {
                        System.out.println("Invalid selection!");
                    }
                    break;

                case 6: // Cancel Reservation
                    if (customers.isEmpty() || buses.isEmpty()) {
                        System.out.println(" No reservations found.");
                        break;
                    }
                    System.out.println("Select Customer to cancel: ");
                    for (int i = 0; i < customers.size(); i++) {
                        System.out.println((i + 1) + ". " + customers.get(i).name);
                    }
                    int cancelCustIndex = scanner.nextInt() - 1;

                    System.out.println("Select Bus: ");
                    for (int i = 0; i < buses.size(); i++) {
                        System.out.println((i + 1) + ". " + buses.get(i).busNumber);
                    }
                    int cancelBusIndex = scanner.nextInt() - 1;

                    if (cancelCustIndex >= 0 && cancelCustIndex < customers.size() && cancelBusIndex >= 0 && cancelBusIndex < buses.size()) {
                        seatRes.cancelReservation(customers.get(cancelCustIndex), buses.get(cancelBusIndex));
                    } else {
                        System.out.println(" Invalid selection!");
                    }
                    break;

                case 7: // Display Reservations
                    seatRes.displayReservations();
                    break;

                case 8: // Exit
                    System.out.println(" Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }
}
