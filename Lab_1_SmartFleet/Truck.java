public class Truck extends Vehicle implements Maintainable {

    private double cargoCapacity;

    public Truck(String vehicleId, String model, double baseRentalRate,
                 double cargoCapacity) {

        super(vehicleId, model, baseRentalRate);
        this.cargoCapacity = cargoCapacity;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public double calculateRentalCost(int days) {

        double cost = getBaseRentalRate() * days;

        if (cargoCapacity > 5) {
            cost += 20 * days;
        }

        return cost;
    }

    @Override
    public void performMaintenance() {
        System.out.println("Performing maintenance on truck " + getVehicleId());
    }

    @Override
    public boolean needsService() {
        return cargoCapacity > 5;
    }
}
