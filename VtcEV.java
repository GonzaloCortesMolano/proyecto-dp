import java.util.List;
import java.util.Iterator;
import java.util.*;

/**
 * Represents a VTC-tier electric vehicle within the simulation.
 * <p>
 * A {@code VtcEV} models the behavior of ride-hailing or transport service
 * vehicles. Its decisions are cost-oriented: when selecting a charging
 * station or charger, it prioritizes the option with the lowest charging
 * fee.
 * </p>
 *
 * This class specializes the charger selection strategy defined in
 * {@link ElectricVehicle}.
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2024.10.07
 */
public class VtcEV extends ElectricVehicle {

    /**
     * Constructs a VTC electric vehicle.
     * <p>
     * The vehicle is initialized with its company, initial location,
     * target destination, identifying information, and battery capacity.
     * Its vehicle tier is set to {@link VehicleTier#VTC}.
     * </p>
     *
     * @param company The operating {@link EVCompany}.
     * @param location The initial {@link Location}.
     * @param targetLocation The destination {@link Location}.
     * @param name The name of the vehicle.
     * @param plate The license plate identifier.
     * @param batteryCapacity The maximum battery capacity in kWh.
     */
    public VtcEV(EVCompany company, Location location, Location targetLocation,
                 String name, String plate, int batteryCapacity) {
        super(company, location, targetLocation, name, plate, batteryCapacity);
        type = VehicleTier.VTC;
    }
    
    /**
     * Determines whether a candidate charger is better than the current best
     * one according to the VTC vehicle strategy.
     * <p>
     * The decision is based exclusively on the charging fee. The charger
     * offering the lowest price per charge is always preferred.
     * </p>
     *
     * @param newCharger The candidate {@link Charger}.
     * @param currentBest The currently selected best charger, or {@code null}.
     * @param newLoc The {@link Location} of the candidate charger.
     * @param bestLoc The {@link Location} of the current best charger.
     * @return {@code true} if the candidate charger has a lower charging fee
     *         than the current best charger.
     */
    @Override
    protected boolean isBetterCharger(Charger newCharger, Charger currentBest,
                                      Location newLoc, Location bestLoc) {
        if (currentBest == null){
            return true;
        }

        return newCharger.getChargingFee() < currentBest.getChargingFee();
    }
   
    /**
     * Selects a free charger from the current charging station.
     * <p>
     * Among all chargers in the station that are able to charge this vehicle,
     * the one with the lowest charging fee is selected.
     * </p>
     *
     * @return The {@link Charger} with the lowest charging fee that can charge
     *         this vehicle, or {@code null} if no suitable charger is available.
     */
    @Override
    public Charger getFreeChargerFromStation(){
        List<Charger> list =
            getCompany().getChargingStation(getRechargingLocation()).getChargers();

        double lowestPrice = Double.MAX_VALUE;
        Charger bestCharger = null;

        for (Charger c : list){
            if (c.canCharge(this)){
                if (c.getChargingFee() < lowestPrice){
                    lowestPrice = c.getChargingFee();
                    bestCharger = c;
                }
            }
        }
        return bestCharger;
    }
    
    /**
     * Compares this VTC electric vehicle with another object for equality.
     * <p>
     * Two {@code VtcEV} objects are considered equal if they represent
     * the same electric vehicle (as defined by
     * {@link ElectricVehicle#equals(Object)}) and are instances of
     * {@code VtcEV}.
     * </p>
     *
     * @param obj The object to compare with.
     * @return {@code true} if both objects represent the same VTC
     *         electric vehicle, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if (super.equals(obj)){
            if (!(obj instanceof VtcEV)){
                return false;
            }
            return true;
        }
        return false;
    }
}
