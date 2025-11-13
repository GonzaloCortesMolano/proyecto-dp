import java.util.*; 

/**
 * Compares two {@link Charger} objects based on their **ID** string in **ascending** order.
 * This is typically used as a final tie-breaker in multi-criteria sorting.
 * * @author DP Clasess
 * @version 2024.10.07
 */
public class ComparatorChargers implements Comparator<Charger>
{
    /**
     * Compares its two {@link Charger} arguments for order.
     * The comparison is based on the lexicographical order of their IDs.
     * @param c1 The first charger to be compared.
     * @param c2 The second charger to be compared.
     * @return A negative integer, zero, or a positive integer as the first 
     * charger's ID is lexicographically less than, equal to, or greater than the second.
     */
    public int compare(Charger c1, Charger c2){ 
        int comparation;
        if(c1.getChargingSpeed()>c2.getChargingSpeed()){
            comparation=-1;
        }
        else if (c1.getChargingSpeed()<c2.getChargingSpeed()){
            comparation=1;
        }
        else{
            if(c1.getChargingFee()<c2.getChargingFee()){
                comparation=-1;
            }
            else if (c1.getChargingFee()>c2.getChargingFee()){
                comparation=1;
            }
            else{
                comparation=c1.getId().compareTo(c2.getId());
            }
        }
       return comparation;
    } 
}