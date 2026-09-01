public abstract class Vehicle {

    // Stores basic vehicle details
    private String vehicleId;
    private String model;
    private double baseRentalRate;

    // Creates a vehicle with its basic details
    public Vehicle(String vehicleId, String model, double baseRentalRate) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.baseRentalRate = baseRentalRate;
    }

    // Returns the vehicle ID
    public String getVehicleId() {
        return vehicleId;
    }

    // Updates the vehicle ID
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    // Returns the vehicle model
    public String getModel() {
        return model;
    }

    // Updates the vehicle model
    public void setModel(String model) {
        this.model = model;
    }

    // Returns the basic rental rate
    public double getBaseRentalRate() {
        return baseRentalRate;
    }

    // Updates the basic rental rate
    public void setBaseRentalRate(double baseRentalRate) {
        this.baseRentalRate = baseRentalRate;
    }

    // Calculates the rental cost for the given number of days
    public abstract double calculateRentalCost(int days);
}
    
