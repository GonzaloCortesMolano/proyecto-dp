import java.util.*; 
import java.util.*; 

/**
 * Comparator for {@link ElectricVehicle} objects based on their license plate.
 * The comparison is performed in ascending lexicographical order of the plates.
 *
 * This comparator can be used to sort lists of electric vehicles in a stable
 * and predictable way.
 *
 * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
 * @version 13-11-2025
 */
public class ComparatorElectricVehicle implements Comparator<ElectricVehicle>
{
    /**
     * Compares two {@link ElectricVehicle} instances for ordering.
     * The comparison is based solely on the lexicographical order of their plates.
     *
     * @param v1 The first electric vehicle to be compared.
     * @param v2 The second electric vehicle to be compared.
     * @return A negative integer, zero, or a positive integer if the plate of the
     *         first vehicle is lexicographically less than, equal to, or greater
     *         than the plate of the second vehicle.
     */
    @Override
    public int compare(ElectricVehicle v1, ElectricVehicle v2){  
        return v1.getPlate().compareTo(v2.getPlate());
    } 
}
