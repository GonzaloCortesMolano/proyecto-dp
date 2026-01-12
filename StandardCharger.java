
/**
 * Represents a standard charging unit that can recharge {@link ElectricVehicle}
 * instances of tier {@link VehicleTier#STANDARD} and {@link VehicleTier#VTC}.
 * <p>
 * This charger does not implement a specific charging fee calculation strategy,
 * therefore it reuses the default behavior provided by the {@link Charger} class
 * following the Template Method design pattern.
 * </p>
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2026.13.1
 */
public class StandardCharger extends Charger
{    

    /**
     * Constructs a {@code StandardCharger} with the given identifier, charging speed
     * and charging fee.
     *
     * @param id    The unique identifier of the charger.
     * @param speed The charging speed in kWh. Must be positive.
     * @param fee   The price per kWh. Must be non-negative.
     * @throws IllegalArgumentException if speed is not positive or fee is negative.
     */
    public StandardCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        types.add(VehicleTier.STANDARD);
        types.add(VehicleTier.VTC);
    }
    
    // NO se implementa calculateFee().
    // Se reutiliza la lógica base del padre automáticamente por Template Method.
    
    /**
     * Indicates whether this charger is equal to another object.
     * Two {@code StandardCharger} objects are considered equal if they
     * are equal according to {@link Charger#equals(Object)} and are instances
     * of the same class with the same supported vehicle types.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the given object represents an equivalent
     *         {@code StandardCharger}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
                if(this == obj) {
                return true; 
            }
            if(!(obj instanceof StandardCharger)) {
                return false;
            }
            //revisa su tipo 
            StandardCharger other=(StandardCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
}