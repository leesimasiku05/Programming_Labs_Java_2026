import java.util.ArrayList;

public class FleetManager {

    public static void main(String[] args) {

        ArrayList<Vehicle> fleet = new ArrayList<>();

        fleet.add(new Car("C001", "Toyota Corolla", 100, true));
        fleet.add(new Car("C002", "Honda Civic", 120, false));

        fleet.add(new Truck("T001", "Volvo FH", 250, 4));
        fleet.add(new Truck("T002", "Scania R500", 300, 8));

        int days = 5;

        System.out.println("SMARTFLEET VEHICLE RENTAL");
        System.out.println("-------------------------");

        for (Vehicle vehicle : fleet) {

            double cost = vehicle.calculateRentalCost(days);

            System.out.println(
                    "Vehicle ID: " + vehicle.getVehicleId()
                    + " | Model: " + vehicle.getModel()
                    + " | Rental Cost: ZMW " + cost
            );
        }
    }
}