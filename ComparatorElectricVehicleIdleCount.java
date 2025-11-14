import java.util.*; 
import java.util.*; 

/**
 * Comparator for {@link ElectricVehicle} objects based on their idle behavior.
 * 
 * <p>The comparison follows this priority:</p>
 * <ol>
 *   <li>Vehicles that are already at their target location come first.</li>
 *   <li>If both are in the same situation regarding the target location,
 *       vehicles with a higher idle count come first.</li>
 *   <li>If still tied, the comparison falls back to sorting by plate using
 *       {@link ComparatorElectricVehicle}.</li>
 * </ol>
 *
 * <p>This comparator is typically used when prioritizing which EV should next be
 * assigned a charger or moved, based on how long they have been idle and whether
 * they reached their intended destination.</p>
 *
 * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
 * @version 13-11-2025
 */
public class ComparatorElectricVehicleIdleCount implements Comparator<ElectricVehicle>
{
    /**
     * Compares two {@link ElectricVehicle} instances based on:
     * <ol>
     *   <li>whether they have reached their target location,</li>
     *   <li>their idle count (descending order),</li>
     *   <li>and finally their license plate as a last tiebreaker.</li>
     * </ol>
     *
     * @param v1 The first electric vehicle to compare.
     * @param v2 The second electric vehicle to compare.
     * @return A negative integer, zero, or a positive integer depending on the
     *         ordering rules described above.
     */
    @Override
    public int compare(ElectricVehicle v1, ElectricVehicle v2){ 
        boolean ev1AtTarget = v1.getLocation().equals(v1.getTargetLocation());
        boolean ev2AtTarget = v2.getLocation().equals(v2.getTargetLocation());
        if(ev1AtTarget && !ev2AtTarget) return -1;
        else if(!ev1AtTarget && ev2AtTarget) return 1;
        else {
            if(v1.getIdleCount()>v2.getIdleCount()) return -1;
            else if(v1.getIdleCount()<v2.getIdleCount()) return 1;
            else return (new ComparatorElectricVehiclePlate().compare(v1,v2));
        }
    } 
}
