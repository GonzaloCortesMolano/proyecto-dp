import java.util.List;
import java.util.Iterator;
import java.util.*;

/**
 * Represents a premium-tier electric vehicle within the simulation.
 * <p>
 * A {@code PremiumEV} prioritizes performance when recharging. Its decision
 * strategy focuses on minimizing charging time by always selecting the
 * fastest available charger, regardless of distance or cost.
 * </p>
 *
 * This class customizes the charger selection logic defined in
 * {@link ElectricVehicle} to favor maximum charging speed.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2024.10.07
 */
public class PremiumEV extends ElectricVehicle {

    /**
     * Constructs a premium electric vehicle.
     * <p>
     * The vehicle is initialized with its company, initial location,
     * target destination, identifying information, and battery capacity.
     * Its vehicle tier is set to {@link VehicleTier#PREMIUM}.
     * </p>
     *
     * @param company The operating {@link EVCompany}.
     * @param location The initial {@link Location}.
     * @param targetLocation The destination {@link Location}.
     * @param name The name of the vehicle.
     * @param plate The license plate identifier.
     * @param batteryCapacity The maximum battery capacity in kWh.
     */
    public PremiumEV(EVCompany company, Location location, Location targetLocation,
                     String name, String plate, int batteryCapacity) {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type = VehicleTier.PREMIUM;
    }

    /**
     * Determines whether a candidate charger is better than the current best
     * one according to the premium vehicle strategy.
     * <p>
     * The comparison is based exclusively on charging speed. The charger with
     * the highest charging speed is always preferred.
     * </p>
     *
     * @param newCharger The candidate {@link Charger}.
     * @param currentBest The currently selected best charger, or {@code null}.
     * @param newLoc The {@link Location} of the candidate charger.
     * @param bestLoc The {@link Location} of the current best charger.
     * @return {@code true} if the candidate charger has a higher charging speed
     *         than the current best charger.
     */
    @Override
    protected boolean isBetterCharger(Charger newCharger, Charger currentBest,
                                      Location newLoc, Location bestLoc) {
        if (currentBest == null){
            return true;    
        }

        return newCharger.getChargingSpeed() > currentBest.getChargingSpeed();
    }
    
    /**
     * Selects a free charger from the current charging station.
     * <p>
     * Among all chargers that are capable of charging this vehicle, the one
     * with the highest charging speed is selected.
     * </p>
     *
     * @return The {@link Charger} with the maximum charging speed that can
     *         charge this vehicle, or {@code null} if no suitable charger
     *         is available.
     */
    @Override
    public Charger getFreeChargerFromStation(){
        List<Charger> list =
            getCompany().getChargingStation(getRechargingLocation()).getChargers();

        int maxSpeed = -1;
        Charger bestCharger = null;

        for (Charger c : list){
            if (c.canCharge(this)){
                if (c.getChargingSpeed() > maxSpeed){
                    maxSpeed = c.getChargingSpeed();
                    bestCharger = c;
                }
            }
        }
        return bestCharger;
    }

    /**
     * Compares this premium electric vehicle with another object for equality.
     * <p>
     * Two {@code PremiumEV} objects are considered equal if they represent
     * the same electric vehicle (as defined by
     * {@link ElectricVehicle#equals(Object)}) and are instances of
     * {@code PremiumEV}.
     * </p>
     *
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same premium
     *         electric vehicle, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if (super.equals(obj)){
            if (!(obj instanceof PremiumEV)){
                return false;
            }
            return true;    
        }
        return false;
    }
}
