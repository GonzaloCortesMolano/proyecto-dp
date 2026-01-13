
/**
 * Represents a high-performance charging unit designed for premium electric vehicles.
 * <p>
 * This charger can only recharge {@link ElectricVehicle} instances of tier
 * {@link VehicleTier#PREMIUM}. It applies a surcharge to the standard charging
 * fee, increasing the final cost by 10%.
 * </p>
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2026.13.1
 */
public class UltraFastCharger extends Charger
{
    
    /**
     * Constructs an {@code UltraFastCharger} with the given identifier,
     * charging speed and base charging fee.
     *
     * @param id    The unique identifier of the charger.
     * @param speed The charging speed in kWh. Must be positive.
     * @param fee   The base price per kWh. Must be non-negative.
     * @throws IllegalArgumentException if speed is not positive or fee is negative.
     */
    public UltraFastCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(VehicleTier.PREMIUM);
    }
    
    /**
     * Calculates the total charging fee for a given amount of energy.
     * <p>
     * The fee is calculated using the base implementation in {@link Charger}
     * and then increased by 10%. The final amount is rounded to two decimal places.
     * </p>
     *
     * @param kwsRecharging The number of kilowatt-hours to recharge.
     * @return The final charging cost including the surcharge.
     */
    @Override
    public double calculateFee(int kwsRecharging) {
        double fee = super.calculateFee(kwsRecharging) * 1.1;
        return Math.round(fee * 100.0)/100.0;
    }
    
    /**
     * Checks whether this {@code UltraFastCharger} is equal to another object.
     * <p>
     * Two {@code UltraFastCharger} objects are equal if they are equal according to
     * {@link Charger#equals(Object)} and support the same vehicle types.
     * </p>
     *
     * @param obj The object to compare with.
     * @return {@code true} if the given object represents an equivalent {@code UltraFastCharger},
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            if(!(obj instanceof UltraFastCharger)) return false;
            UltraFastCharger other = (UltraFastCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
    
}