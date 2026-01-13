
/**
 * Represents a charging unit reserved for priority electric vehicles.
 * <p>
 * This charger can only recharge {@link ElectricVehicle} instances
 * of tier {@link VehicleTier#PRIORITY}. It follows the standard charging
 * behavior defined in {@link Charger}, without modifying the fee
 * calculation or recharge process.
 * </p>
 *
 * @author Ricardo Álvarez, Gonzalo Cortés y Sergio Zambrano
 * @version 2026.13.1
 */
public class PriorityCharger extends Charger
{
    /**
     * Constructs a {@code PriorityCharger} with the given identifier,
     * charging speed and charging fee.
     *
     * @param id    The unique identifier of the charger.
     * @param speed The charging speed in kWh. Must be positive.
     * @param fee   The price per kWh. Must be non-negative.
     * @throws RuntimeException if {@code id} is null or empty, {@code speed} {@code fee} are lower or equal than 0.
     * @throws IllegalArgumentException if types is null.
     */
    public PriorityCharger(String id, int speed, double fee)
    {
        super(id, speed, fee);
        try{
            if(this.types==null) {throw new NullPointerException();}
            types.add(VehicleTier.PRIORITY);
        } catch(NullPointerException e){
            System.err.println("Error in the creation of the PriorityCharger");
        }
    }
    
    /**
     * Indicates whether this charger is equal to another object.
     * Two {@code PriorityCharger} objects are considered equal if they
     * are equal according to {@link Charger#equals(Object)} and are
     * instances of the same class with identical supported vehicle types.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the given object represents an equivalent
     *         {@code PriorityCharger}, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(super.equals(obj)) {
            //revisa los tipos que admite
            if(!(obj instanceof PriorityCharger)) return false;
            PriorityCharger other = (PriorityCharger) obj;
            return this.types.equals(other.types);
        }
        return false;
    }
}