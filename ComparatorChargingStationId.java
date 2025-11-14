import java.util.*; 
import java.util.*; 

/**
 * A comparator for ordering {@link ChargingStation} objects by their ID
 * in ascending lexicographical order.
 * This comparator can be used to sort collections of charging stations
 * based on their identifiers.
 *
 * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
 * @version 13-11-2025
 */
public class ComparatorChargingStationId implements Comparator<ChargingStation>
{
    /**
     * Compares two {@link ChargingStation} instances by their station IDs.
     * 
     * The comparison is performed using the natural lexicographical order
     * of the station ID strings.
     *
     * @param st1 The first charging station to compare (must not be {@code null}).
     * @param st2 The second charging station to compare (must not be {@code null}).
     * @return A negative integer, zero, or a positive integer depending on whether
     *         the ID of {@code st1} is lexicographically less than, equal to, or
     *         greater than the ID of {@code st2}.
     */
    public int compare(ChargingStation st1, ChargingStation st2){ 
        return st1.getId().compareTo(st2.getId());
    } 
}