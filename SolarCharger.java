
/**
 * Represents a solar-powered charging unit that can recharge
 * {@link ElectricVehicle} instances of tier {@link VehicleTier#VTC}.
 * <p>
 * This charger applies a discount to the base charging fee, simulating
 * the use of renewable solar energy.
 * </p>
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2026.13.1
 */
public class SolarCharger extends Charger
{
    /**
     * Constructs a {@code SolarCharger} with the given identifier, charging speed
     * and charging fee.
     *
     * @param id    The unique identifier of the charger.
     * @param speed The charging speed in kWh. Must be positive.
     * @param fee   The base price per kWh. Must be non-negative.
     * @throws IllegalArgumentException if speed is not positive or fee is negative.
     */
    public SolarCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(VehicleTier.VTC);
    }

    /**
     * Calculates the total charging cost applying a solar discount.
     * <p>
     * The final price is 10% cheaper than the base fee calculated by
     * {@link Charger#calculateFee(int)}.
     * </p>
     *
     * @param kwsRecharging The number of kilowatt-hours recharged.
     * @return The discounted charging cost.
     */
    public double calculateFee(int kwsRecharging){
        return (super.calculateFee(kwsRecharging))*0.9;
    }
    
    /**
     * Indicates whether this charger is equal to another object.
     * Two {@code SolarCharger} objects are considered equal if they
     * are equal according to {@link Charger#equals(Object)} and are
     * instances of the same class with identical supported vehicle types.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the given object represents an equivalent
     *         {@code SolarCharger}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            if(!(obj instanceof SolarCharger)) return false;
            SolarCharger other = (SolarCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
}