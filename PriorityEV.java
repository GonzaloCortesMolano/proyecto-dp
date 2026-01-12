import java.util.Iterator;
import java.util.List;

/**
 * Represents a priority-tier electric vehicle within the simulation.
 * <p>
 * A {@code PriorityEV} has preferential behavior compared to standard
 * vehicles. It moves at a higher effective speed and does not notify
 * the operating company when recharging.
 * </p>
 *
 * When selecting a charging station, this vehicle prioritizes chargers
 * that minimize the remaining distance to the final target destination,
 * regardless of the distance to the station itself.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2024.10.07
 */
public class PriorityEV extends ElectricVehicle
{
    
    /**
     * Constructs a priority electric vehicle.
     * <p>
     * The vehicle is initialized with its company, initial location,
     * target destination, identifying information, and battery capacity.
     * Its vehicle tier is set to {@link VehicleTier#PRIORITY}.
     * </p>
     *
     * @param company The operating {@link EVCompany}.
     * @param location The initial {@link Location}.
     * @param targetLocation The destination {@link Location}.
     * @param name The name of the vehicle.
     * @param plate The license plate identifier.
     * @param batteryCapacity The maximum battery capacity in kWh.
     */
    public PriorityEV(EVCompany company, Location location, Location targetLocation,
                      String name, String plate, int batteryCapacity)
    {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type = VehicleTier.PRIORITY;
    }
    
    /**
     * Overrides the company notification behavior.
     * <p>
     * Priority vehicles do not notify the operating company when a
     * recharging operation is performed.
     * </p>
     *
     * @param charger The {@link Charger} used for recharging.
     */
    @Override
    protected void notifyCompany(Charger charger) {
        // No action required for priority vehicles.
    }
    
    /**
     * Moves the vehicle towards its destination.
     * <p>
     * A priority vehicle moves with double speed: it performs one standard
     * movement step and, if it has not yet reached a target or charging
     * station and still has battery remaining, it performs an additional
     * movement step during the same simulation step.
     * </p>
     *
     * @param step The current simulation step.
     */
    @Override
    protected void move(int step) {
        super.move(step);
        
        if (!isInTarget() && !isInStation() && getBatteryLevel() > 0) {
            super.move(step);
        }
    }
    
    /**
     * Determines whether a candidate charger is better than the current best
     * one according to the priority vehicle strategy.
     * <p>
     * The decision is based solely on how close the charging station is to
     * the final target destination. The charger that minimizes the remaining
     * distance to the target is preferred.
     * </p>
     *
     * @param newCharger The candidate {@link Charger}.
     * @param currentBest The currently selected best charger, or {@code null}.
     * @param newLoc The {@link Location} of the candidate charger.
     * @param bestLoc The {@link Location} of the current best charger.
     * @return {@code true} if the candidate charger is closer to the final
     *         destination than the current best charger.
     */
    @Override
    protected boolean isBetterCharger(Charger newCharger, Charger currentBest,
                                      Location newLoc, Location bestLoc)
    {
        if (currentBest == null) {
            return true;   
        }

        int newDistToTarget = newLoc.distance(this.getTargetLocation());
        int currentDistToTarget = bestLoc.distance(this.getTargetLocation());

        return newDistToTarget < currentDistToTarget;
    } 
   
    /**
     * Compares this priority electric vehicle with another object for equality.
     * <p>
     * Two {@code PriorityEV} objects are considered equal if they represent
     * the same electric vehicle (as defined by
     * {@link ElectricVehicle#equals(Object)}) and are instances of
     * {@code PriorityEV}.
     * </p>
     *
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same priority
     *         electric vehicle, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if (super.equals(obj)) {
            if (!(obj instanceof PriorityEV)) {
                return false;
            }
            return true;
        }
        return false;
    }
}
