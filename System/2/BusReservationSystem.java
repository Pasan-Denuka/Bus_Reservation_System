import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// Node class for Stack (Linked List Implementation)
class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

// Dynamic Stack Implementation (Linked List-based)
class DynamicStack<T> {
    private Node<T> top;

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return null;
        }
        T data = top.data;
        top = top.next;
        return data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.println("Stack contents (Newest to Oldest):");
        Node<T> temp = top;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
}

// Customer Class
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

// Bus Class
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
        return busNumber + " (" + startPoint + " -> " + endPoint + ", Seats: " + availableSeats + "/" + totalSeats + ", Fare: " + fare + ")";
    }
}

// Reservation Class
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

// Bus Seat Reservation System (Queue for Waiting List)
class BusSeatReservation {
    LinkedList<Reservation> reservations = new LinkedList<>();
    Queue<Customer> waitingQueue = new LinkedList<>();

    public void reserveSeat(Customer customer, Bus bus) {
        if (bus.availableSeats > 0) {
            int seatNumber = bus.totalSeats - bus.availableSeats + 1;
            Reservation res = new Reservation(customer, bus, seatNumber);
            reservations.add(res);
            bus.availableSeats--;
            System.out.println("Seat " + seatNumber + " reserved for " + customer.name + " on bus " + bus.busNumber);
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
            System.out.println("Reservation cancelled for " + customer.name + " on bus " + bus.busNumber);

            // Assign seat to next waiting customer
            if (!waitingQueue.isEmpty()) {
                Customer nextCustomer = waitingQueue.poll();
                reserveSeat(nextCustomer, bus);
                System.out.println("Seat allocated to next customer in queue: " + nextCustomer.name);
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }

    public void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations made.");
        } else {
            System.out.println("Current Reservations:");
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }
}

// Main Class
public class BusReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DynamicStack<Customer> customerStack = new DynamicStack<>();
        DynamicStack<Bus> busStack = new DynamicStack<>();
        BusSeatReservation seatRes = new BusSeatReservation();

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
                    customerStack.push(new Customer(name, mobile, email, city, age));
                    System.out.println("Customer registered successfully.");
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
                    busStack.push(new Bus(busNumber, totalSeats, startPoint, endPoint, fare));
                    System.out.println("Bus registered successfully.");
                    break;

                case 3: // Display Customers
                    customerStack.display();
                    break;

                case 4: // Display Buses
                    busStack.display();
                    break;

                case 5: // Reserve Seat
                    if (customerStack.isEmpty() || busStack.isEmpty()) {
                        System.out.println("No registered customers or buses.");
                        break;
                    }
                    Customer customer = customerStack.pop();
                    Bus bus = busStack.pop();
                    seatRes.reserveSeat(customer, bus);
                    break;

                case 6: // Cancel Reservation
                    System.out.print("Enter Customer Name: ");
                    String cancelName = scanner.next();
                    System.out.print("Enter Bus Number: ");
                    String cancelBusNum = scanner.next();
                    seatRes.cancelReservation(new Customer(cancelName, "", "", "", 0), new Bus(cancelBusNum, 0, "", "", 0));
                    break;

                case 7: // Display Reservations
                    seatRes.displayReservations();
                    break;

                case 8: // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
