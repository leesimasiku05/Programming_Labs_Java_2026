public class Car extends Vehicle {

    private boolean hasGPS;

    public Car(String vehicleId, String model, double baseRentalRate, boolean hasGPS) {
        super(vehicleId, model, baseRentalRate);
        this.hasGPS = hasGPS;
    }

    public boolean isHasGPS() {
        return hasGPS;
    }

    public void setHasGPS(boolean hasGPS) {
        this.hasGPS = hasGPS;
    }

    @Override
    public double calculateRentalCost(int days) {

        double cost = getBaseRentalRate() * days;

        if (hasGPS) {
            cost += 5 * days;
        }

        return cost;
    }
}
