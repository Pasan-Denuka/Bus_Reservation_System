import java.util.*;

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

class CustomerRegistration {
    Stack<Customer> customerStack = new Stack<>();

    public void registerCustomer(String name, String mobile, String email, String city, int age) {
        Customer customer = new Customer(name, mobile, email, city, age);
        customerStack.push(customer);
        System.out.println("Customer Registered: " + name);
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

class BusRegistration {
    Stack<Bus> busStack = new Stack<>();

    public void registerBus(String busNumber, int totalSeats, String startPoint, String endPoint, double fare) {
        Bus bus = new Bus(busNumber, totalSeats, startPoint, endPoint, fare);
        busStack.push(bus);
        System.out.println("Bus Registered: " + busNumber);
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

class BusSeatReservation {
    LinkedList<Reservation> reservations = new LinkedList<>();
    Queue<Customer> waitingQueue = new LinkedList<>();

    public void reserveSeat(Customer customer, Bus bus) {
        if (bus.availableSeats > 0) {
            int seatNumber = (bus.totalSeats - bus.availableSeats) + 1;
            Reservation res = new Reservation(customer, bus, seatNumber);
            reservations.add(res);
            bus.availableSeats--;
            System.out.println("Seat " + seatNumber + " reserved for " + customer.name + " on Bus " + bus.busNumber);
        } else {
            waitingQueue.add(customer);
            System.out.println("No available seats. " + customer.name + " added to waiting list.");
        }
    }

    public void cancelReservation(Customer customer, Bus bus) {
        Reservation toRemove = null;
        for (Reservation res : reservations) {
            if (res.customer == customer && res.bus == bus) {
                toRemove = res;
                break;
            }
        }

        if (toRemove != null) {
            reservations.remove(toRemove);
            bus.availableSeats++;
            System.out.println("Reservation cancelled for " + customer.name + " on Bus " + bus.busNumber);

            if (!waitingQueue.isEmpty()) {
                Customer nextCustomer = waitingQueue.poll();
                reserveSeat(nextCustomer, bus);
                System.out.println("Seat allocated to next waiting customer: " + nextCustomer.name);
            }
        } else {
            System.out.println("No reservation found for this customer.");
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
        }S
    }
}

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
                case 1:
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

                case 2:
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

                case 3:
                    customerReg.displayCustomers();
                    break;

                case 4:
                    busReg.displayBuses();
                    break;

                case 5:
                    if (!customers.isEmpty() && !buses.isEmpty()) {
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

                        seatRes.reserveSeat(customers.get(custIndex), buses.get(busIndex));
                    } else {
                        System.out.println("No registered customers or buses.");
                    }
                    break;

                case 6:
                    if (!customers.isEmpty() && !buses.isEmpty()) {
                        System.out.println("Select Customer to Cancel: ");
                        for (int i = 0; i < customers.size(); i++) {
                            System.out.println((i + 1) + ". " + customers.get(i).name);
                        }
                        int cancelCustIndex = scanner.nextInt() - 1;

                        System.out.println("Select Bus: ");
                        for (int i = 0; i < buses.size(); i++) {
                            System.out.println((i + 1) + ". " + buses.get(i).busNumber);
                        }
                        int cancelBusIndex = scanner.nextInt() - 1;

                        seatRes.cancelReservation(customers.get(cancelCustIndex), buses.get(cancelBusIndex));
                    } else {
                        System.out.println("No reservations found.");
                    }
                    break;

                case 7:
                    seatRes.displayReservations();
                    break;

                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
            }
        }
    }
}
