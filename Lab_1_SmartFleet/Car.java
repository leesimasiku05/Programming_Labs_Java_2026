// Car inherits all acccessibe properties and methods from Vehicle
public class Car extends Vehicle {
  
    // Declares a private boolean variable to store whether the car has GPS.
    // Can only be accessed inside Car.
    private boolean hasGPS;

    // Constructor for creating a Car object.
    // It receives the vehicle ID, model, rental, rate, and GPS information.
    public Car(String vehicleId, String model, double baseRentalRate, boolean hasGPS) {
        // Calls construtor of parent class (Vehicle).
        // Passes the vehicle ID, model, abd rental rate to Vehicle.
        super(vehicleId, model, baseRentalRate);
        // Stores GPS value receieved by the constructor in the Car object's hasGPS variable.
        this.hasGPS = hasGPS;
    }
    
    // Getter method for checking whethr the car has GPS.
    public boolean isHasGPS() {
        // Returns the current value of hasGPS.It will either be true or False.
        return hasGPS;
    }
    
    // Setter method for changing the GPS value.
    // It recieves a new true/false value.
    public void setHasGPS(boolean hasGPS) {
        // Updates the object hasGPS value with the value supplies to method.
        this.hasGPS = hasGPS;
    }
    
    // Indicates that this method overrides a method that already exists in the Vehicle parent class.
    @Override
    // Calculates the total rental cost for a given number of days.
    public double calculateRentalCost(int days) {
      
        // Calculates the basic rental cost:
        // daily rental multiplied by the number of days
        double cost = getBaseRentalRate() * days;
        
        // Chekcs whether this car has GPS.
        if (hasGPS) {
            // Adds an additional $5 for GPS every rental day.
            cost += 5 * days;
        }
        
        // Return the final rental cost.
        return cost;
    }
}
