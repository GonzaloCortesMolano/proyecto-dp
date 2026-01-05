import java.util.*; 

/**
 * A comparator for ordering {@link Charger} objects based on multiple criteria.
 * <p>
 * The comparison follows this priority:
 * <ol>
 *   <li><strong>Charging speed</strong> (higher speed comes first)</li>
 *   <li><strong>Charging fee</strong> (lower fee comes first)</li>
 *   <li><strong>ID</strong> string in ascending lexicographical order
 *       (used as a final tie-breaker)</li>
 * </ol>
 * This comparator is typically used when chargers need to be ranked by efficiency
 * and cost, with consistent ordering guaranteed by the final ID comparison.
 * </p>
 *
 * @author: Sergio Zambrano, Gonzalo Cortes, Ricardo Alvarez
 * @version 13-11-2025
 */
public class ComparatorChargers implements Comparator<Charger>
{
    /**
     * Compares two {@link Charger} instances according to the defined
     * multi-criteria ordering.
     * <p>
     * The comparison rules are:
     * <ul>
     *   <li>Higher charging speed → comes first.</li>
     *   <li>If equal speed, lower charging fee → comes first.</li>
     *   <li>If both speed and fee are equal, chargers are ordered by ID
     *       lexicographically (ascending).</li>
     * </ul>
     * </p>
     *
     * @param c1 The first charger to compare (must not be {@code null}).
     * @param c2 The second charger to compare (must not be {@code null}).
     * @return A negative integer, zero, or a positive integer depending on
     *         the order of the two chargers following the described criteria.
     */
    
     
     public int compare(Charger c1, Charger c2){ 
        int comparation;
        if(c1.getChargingSpeed()>c2.getChargingSpeed()){
            comparation=-1;
        }
        else if (c1.getChargingSpeed() < c2.getChargingSpeed()){
            comparation=1;
        }
        else{
            if(c1.getChargingFee() < c2.getChargingFee()){
                comparation=-1;
            }
            else if (c1.getChargingFee() > c2.getChargingFee()){
                comparation=1;
            }
            else{
                comparation=c1.getId().compareTo(c2.getId());
            }
        }
       return comparation;
    } 
    
   
}