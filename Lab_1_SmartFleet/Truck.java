public class Truck extends Vehicle implements Maintainable {

  // Stores the truck's cargo capacity
  private double cargoCapacity;

  // Creates a new truck
  public Truck(String vehicleId, String model, double baseRentalRate,
             double cargoCapacity) {

    super(vehicleId, model, baseRentalRate);
    this.cargoCapacity = cargoCapacity;
  }

  // Gets the cargo capacity
  public double getCargoCapacity() {
    return cargoCapacity;
  }

  // Updates the cargo capacity
  public void setCargoCapacity(double cargoCapacity) {
    this.cargoCapacity = cargoCapacity;
  }

  // Calculates the total rental cost
  @Override
  public double calculateRentalCost(int rentalDays) {

    double rentalCost = getBaseRentalRate() * rentalDays;

    // Add extra charge for large cargo
    if (cargoCapacity > 5) {
        rentalCost += 20 * rentalDays;
    }

    return rentalCost;
  }

  // Performs truck maintenance
  @Override
  public void performMaintenance() {
    System.out.println(
            "Performing maintenance on truck " + getVehicleId()
    );
  }

  // Checks if the truck needs servicing
  @Override
  public boolean needsService() {
    return cargoCapacity > 5;
  }
}
