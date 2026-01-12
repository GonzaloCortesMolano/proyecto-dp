import java.util.List;
import java.util.Iterator;

/**
 * Represents a standard-tier electric vehicle within the simulation.
 * <p>
 * A {@code StandardEV} follows a simple routing strategy when selecting
 * a charging station: it prioritizes the option that minimizes the total
 * distance from its current location to the charging station and then
 * to the final target destination.
 * </p>
 *
 * This class specializes the charger selection logic defined in
 * {@link ElectricVehicle}.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2024.10.07
 */
public class StandardEV extends ElectricVehicle
{
    /**
     * Constructs a standard electric vehicle.
     * <p>
     * The vehicle is initialized with its company, initial location,
     * target destination, identifying information, and battery capacity.
     * Its vehicle tier is set to {@link VehicleTier#STANDARD}.
     * </p>
     *
     * @param company The operating {@link EVCompany}.
     * @param location The initial {@link Location}.
     * @param targetLocation The destination {@link Location}.
     * @param name The name of the vehicle.
     * @param plate The license plate identifier.
     * @param batteryCapacity The maximum battery capacity in kWh.
     */
    public StandardEV(EVCompany company, Location location, Location targetLocation,
                      String name, String plate, int batteryCapacity)
    {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type = VehicleTier.STANDARD;
    }

    /**
     * Determines whether a candidate charger is better than the current best one
     * according to the standard vehicle strategy.
     * <p>
     * The comparison is based on the total distance required to travel from
     * the vehicle's current location to the charging station and then from
     * the station to the final target destination.
     * </p>
     *
     * @param newCharger The candidate {@link Charger}.
     * @param currentBest The currently selected best charger, or {@code null}.
     * @param newLoc The {@link Location} of the candidate charger.
     * @param bestLoc The {@link Location} of the current best charger.
     * @return {@code true} if the candidate charger yields a shorter total
     *         distance, {@code false} otherwise.
     */
    @Override 
    protected boolean isBetterCharger(Charger newCharger, Charger currentBest,
                                      Location newLoc, Location bestLoc)
    {
        if (currentBest == null){
            return true;
        }

        int newTotalDist = this.getLocation().distance(newLoc)
                         + newLoc.distance(this.getTargetLocation());
        int currentTotalDist = this.getLocation().distance(bestLoc)
                             + bestLoc.distance(this.getTargetLocation());

        return newTotalDist < currentTotalDist;
    }    
   
    /**
     * Compares this standard electric vehicle with another object for equality.
     * <p>
     * Two {@code StandardEV} objects are considered equal if they represent
     * the same electric vehicle (as defined by
     * {@link ElectricVehicle#equals(Object)}) and are instances of
     * {@code StandardEV}.
     * </p>
     *
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same standard
     *         electric vehicle, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (super.equals(obj)) {
            if (!(obj instanceof StandardEV)) {
                return false;
            }
            return true;
        }
        return false;
    }
}
