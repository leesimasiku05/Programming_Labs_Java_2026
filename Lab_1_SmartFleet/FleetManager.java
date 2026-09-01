import java.util.ArrayList;

public class FleetManager {

  public static void main(String[] args) {

    // Create the vehicle fleet
    ArrayList<Vehicle> fleet = createFleet();

    // Display the rental report
    displayRentalReport(fleet, 5);
  }

  // Creates and returns the vehicle fleet
  public static ArrayList<Vehicle> createFleet() {

    ArrayList<Vehicle> fleet = new ArrayList<>();

    fleet.add(new Car("C001", "Toyota Corolla", 100, true));
    fleet.add(new Car("C002", "Honda Civic", 120, false));

    fleet.add(new Truck("T001", "Volvo FH", 250, 4));
    fleet.add(new Truck("T002", "Scania R500", 300, 8));

    return fleet;
  }

  // Displays the rental cost for each vehicle
  public static void displayRentalReport(
        ArrayList<Vehicle> fleet,
        int rentalDays) {

    System.out.println("SMARTFLEET VEHICLE RENTAL");
    System.out.println("-------------------------");

    for (Vehicle vehicle : fleet) {

        double rentalCost =
                vehicle.calculateRentalCost(rentalDays);

        System.out.println(
                "Vehicle ID: " + vehicle.getVehicleId()
                + " | Model: " + vehicle.getModel()
                + " | Rental Cost: ZMW " + rentalCost
        );
    }
  }
}
