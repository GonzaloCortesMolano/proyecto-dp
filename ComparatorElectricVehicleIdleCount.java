import java.util.*; 
import java.util.*; 

/**
 * Compares two {@link ChargingStation} objects based on their ID in ascending order.
 * This can be used for sorting lists of charging stations.
 * @author DP Clasess
 * @version 2023
 */
public class ComparatorElectricVehicleIdleCount implements Comparator<ElectricVehicle>
{
    /**
     * Compares its two {@link ElectricVehicle} arguments for order.
     * The comparison is based on the lexicographical order of their idle time.
     * @param st1 The first vehicle to be compared.
     * @param st2 The second vehicle to be compared.
     * @return A negative integer, zero, or a positive integer as the first
     * vehicle's idle time is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(ElectricVehicle v1, ElectricVehicle v2){  
        if(v1.getIdleCount()<v2.getIdleCount()) return -1;
        else if(v1.getIdleCount()>v2.getIdleCount()) return 1;
        else return 0;
    } 
}
